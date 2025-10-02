package br.edu.infnet.servicos.controller;

import java.util.List;
import java.util.stream.Collectors;

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

import br.edu.infnet.servicos.dto.request.ClienteCreateRequestDTO;
import br.edu.infnet.servicos.dto.request.ClienteFidelidadeRequestDTO;
import br.edu.infnet.servicos.dto.response.ClienteResponseDTO;
import br.edu.infnet.servicos.model.domain.Cliente;
import br.edu.infnet.servicos.model.domain.Endereco;
import br.edu.infnet.servicos.service.ClienteService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

	private final ClienteService clienteService;

	public ClienteController(ClienteService clienteService) {
		this.clienteService = clienteService;
	}

	@GetMapping
	public List<ClienteResponseDTO> obterClientes() {
		return clienteService.obterLista().stream()
				.map(ClienteResponseDTO::new)
				.collect(Collectors.toList());
	}

	@GetMapping(value = "/{id}")
	public ClienteResponseDTO obterPorId(@PathVariable Integer id) {
		Cliente cliente = clienteService.obterPorId(id);
		return new ClienteResponseDTO(cliente);
	}

	@PostMapping
	public ResponseEntity<ClienteResponseDTO> incluirCliente(@Valid @RequestBody ClienteCreateRequestDTO clienteRequest) {
		Cliente cliente = convertToEntity(clienteRequest);
		Cliente clienteSalvo = clienteService.incluir(cliente);
		return ResponseEntity.status(HttpStatus.CREATED).body(new ClienteResponseDTO(clienteSalvo));
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<ClienteResponseDTO> alterarCliente(@PathVariable Integer id, @Valid @RequestBody ClienteCreateRequestDTO clienteRequest) {
		Cliente cliente = convertToEntity(clienteRequest);
		Cliente clienteAlterado = clienteService.alterar(id, cliente);
		return ResponseEntity.ok(new ClienteResponseDTO(clienteAlterado));
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> excluirCliente(@PathVariable Integer id) {
		clienteService.excluir(id);
		return ResponseEntity.noContent().build();
	}

	@PatchMapping(value = "/{id}/fidelidade")
	public ResponseEntity<ClienteResponseDTO> atualizarFidelidade(@PathVariable Integer id, @Valid @RequestBody ClienteFidelidadeRequestDTO request) {
		Cliente clienteAtualizado = clienteService.atualizarFidelidade(id, request.getFidelidade());
		return ResponseEntity.ok(new ClienteResponseDTO(clienteAtualizado));
	}

	// Método helper para converter DTO para entidade
	private Cliente convertToEntity(ClienteCreateRequestDTO dto) {
		Cliente cliente = new Cliente();
		cliente.setNome(dto.getNome());
		cliente.setEmail(dto.getEmail());
		cliente.setCpf(dto.getCpf());
		cliente.setTelefone(dto.getTelefone());
		cliente.setFidelidade(dto.getFidelidade());

		if (dto.getEndereco() != null) {
			Endereco endereco = new Endereco();
			endereco.setCep(dto.getEndereco().getCep());
			endereco.setLogradouro(dto.getEndereco().getLogradouro());
			endereco.setComplemento(dto.getEndereco().getComplemento());
			endereco.setUnidade(dto.getEndereco().getUnidade());
			endereco.setBairro(dto.getEndereco().getBairro());
			endereco.setLocalidade(dto.getEndereco().getLocalidade());
			endereco.setUf(dto.getEndereco().getUf());
			endereco.setEstado(dto.getEndereco().getEstado());
			cliente.setEndereco(endereco);
		}

		return cliente;
	}
}