package br.edu.infnet.servicos.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.infnet.servicos.dto.request.LoginRequestDTO;
import br.edu.infnet.servicos.dto.response.LoginResponseDTO;
import br.edu.infnet.servicos.model.domain.Cliente;
import br.edu.infnet.servicos.model.domain.Funcionario;
import br.edu.infnet.servicos.repository.ClienteRepository;
import br.edu.infnet.servicos.repository.FuncionarioRepository;
import br.edu.infnet.servicos.security.JwtUtil;
import jakarta.validation.Valid;

import java.util.Arrays;
import java.util.Optional;

@RestController
@RequestMapping("/")
public class ApiController {

	private final AuthenticationManager authenticationManager;
	private final ClienteRepository clienteRepository;
	private final FuncionarioRepository funcionarioRepository;
	private final JwtUtil jwtUtil;

	public ApiController(AuthenticationManager authenticationManager, 
	                    ClienteRepository clienteRepository,
	                    FuncionarioRepository funcionarioRepository,
	                    JwtUtil jwtUtil) {
		this.authenticationManager = authenticationManager;
		this.clienteRepository = clienteRepository;
		this.funcionarioRepository = funcionarioRepository;
		this.jwtUtil = jwtUtil;
	}

	@GetMapping
	public String hello() {
		return "Hello!!! - Cochito Services API";
	}

	@PostMapping("/api/auth/login")
	public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO loginRequest) {
		try {
			// Autentica o usuário
			Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
					loginRequest.getEmail(),
					loginRequest.getPassword()
				)
			);

			// Define o contexto de segurança
			SecurityContextHolder.getContext().setAuthentication(authentication);

			// Busca o usuário para retornar informações
			String email = loginRequest.getEmail();
			
			// Gera o JWT
			String jwt = jwtUtil.generateToken(authentication);

			// Verifica se é funcionário
			Optional<Funcionario> funcionario = funcionarioRepository.findByEmail(email);
			if (funcionario.isPresent()) {
				Funcionario f = funcionario.get();
				LoginResponseDTO.UserInfoDTO userInfo = new LoginResponseDTO.UserInfoDTO(
					f.getId(),
					f.getNome(),
					f.getEmail(),
					f.obterTipo(),
					Arrays.asList("ADMIN")
				);

				return ResponseEntity.ok(new LoginResponseDTO(true, "Login realizado com sucesso", jwt, userInfo));
			}

			// Verifica se é cliente
			Optional<Cliente> cliente = clienteRepository.findByEmail(email);
			if (cliente.isPresent()) {
				Cliente c = cliente.get();
				LoginResponseDTO.UserInfoDTO userInfo = new LoginResponseDTO.UserInfoDTO(
					c.getId(),
					c.getNome(),
					c.getEmail(),
					c.obterTipo(),
					Arrays.asList("USER")
				);

				return ResponseEntity.ok(new LoginResponseDTO(true, "Login realizado com sucesso", jwt, userInfo));
			}

			throw new RuntimeException("Usuário não encontrado após autenticação");

		} catch (BadCredentialsException e) {
			LoginResponseDTO response = new LoginResponseDTO(false, "Credenciais inválidas", null, null);
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
		} catch (Exception e) {
			LoginResponseDTO response = new LoginResponseDTO(false, "Erro interno do servidor", null, null);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}
}
