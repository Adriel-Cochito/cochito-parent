package br.edu.infnet.servicos.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.infnet.servicos.dto.request.FuncionarioRequestDTO;
import br.edu.infnet.servicos.dto.response.FuncionarioResponseDTO;
import br.edu.infnet.servicos.service.FuncionarioService;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/funcionarios")
@Tag(name = "5. Funcionários", description = "Gerenciamento de funcionários")
public class FuncionarioController {

	private final FuncionarioService funcionarioService;

	public FuncionarioController(FuncionarioService funcionarioService) {
		this.funcionarioService = funcionarioService;
	}

	@PostMapping
	public ResponseEntity<FuncionarioResponseDTO> incluirFuncionario(@Valid @RequestBody FuncionarioRequestDTO funcionarioRequest) {
		FuncionarioResponseDTO response = funcionarioService.criarComDTO(funcionarioRequest);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@GetMapping
	public ResponseEntity<List<FuncionarioResponseDTO>> obterFuncionarios() {
		List<FuncionarioResponseDTO> funcionarios = funcionarioService.listarTodosComDTO();
		return ResponseEntity.ok(funcionarios);
	}

	@GetMapping("/{id}")
	public ResponseEntity<FuncionarioResponseDTO> obterPorId(@PathVariable Integer id) {
		FuncionarioResponseDTO funcionario = funcionarioService.buscarPorIdComDTO(id);
		return ResponseEntity.ok(funcionario);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> excluirFuncionario(@PathVariable Integer id) {
		funcionarioService.excluir(id);
		return ResponseEntity.noContent().build();
	}

	@PutMapping("/{id}")
	public ResponseEntity<FuncionarioResponseDTO> alterarFuncionario(@PathVariable Integer id,
			@Valid @RequestBody FuncionarioRequestDTO funcionarioRequest) {
		FuncionarioResponseDTO response = funcionarioService.atualizarComDTO(id, funcionarioRequest);
		return ResponseEntity.ok(response);
	}

	@PatchMapping("/{id}/inativar")
	public ResponseEntity<FuncionarioResponseDTO> inativarFuncionario(@PathVariable Integer id) {
		FuncionarioResponseDTO response = funcionarioService.inativarComDTO(id);
		return ResponseEntity.ok(response);
	}
}