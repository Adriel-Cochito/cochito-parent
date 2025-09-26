package br.edu.infnet.servicos.controller;

import java.util.List;
import java.util.Map;

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

import br.edu.infnet.servicos.model.domain.Cliente;
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
	public List<Cliente> obterClientes() {
		return clienteService.obterLista();
	}

	@GetMapping(value = "/{id}")
	public Cliente obterPorId(@PathVariable Integer id) {
		return clienteService.obterPorId(id);
	}

	@PostMapping
	public ResponseEntity<Cliente> incluirCliente(@Valid @RequestBody Cliente cliente) {
		Cliente clienteSalvo = clienteService.incluir(cliente);
		return ResponseEntity.status(HttpStatus.CREATED).body(clienteSalvo);
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<Cliente> alterarCliente(@PathVariable Integer id, @Valid @RequestBody Cliente cliente) {
		Cliente clienteAlterado = clienteService.alterar(id, cliente);
		return ResponseEntity.ok(clienteAlterado);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> excluirCliente(@PathVariable Integer id) {
		clienteService.excluir(id);
		return ResponseEntity.noContent().build();
	}

	@PatchMapping(value = "/{id}/fidelidade")
	public ResponseEntity<Cliente> atualizarFidelidade(@PathVariable Integer id, @RequestBody Map<String, String> request) {
		String fidelidade = request.get("fidelidade");
		return ResponseEntity.ok(clienteService.atualizarFidelidade(id, fidelidade));
	}

}