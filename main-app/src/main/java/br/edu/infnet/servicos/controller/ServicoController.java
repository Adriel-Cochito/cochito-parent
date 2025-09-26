package br.edu.infnet.servicos.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.infnet.servicos.model.domain.Servico;
import br.edu.infnet.servicos.service.ServicoService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/servicos")
public class ServicoController {

    private final ServicoService servicoService;
	
	public ServicoController(ServicoService servicoService) {
		this.servicoService = servicoService;
	}

	@PostMapping
	public ResponseEntity<Servico> incluirServico(@Valid @RequestBody Servico servico) {
		Servico servicoSalvo = servicoService.incluir(servico);
		return ResponseEntity.status(HttpStatus.CREATED).body(servicoSalvo);
	}
	
	@GetMapping
	public List<Servico> obterServico() {
		return servicoService.obterLista();
	}

	@GetMapping(value = "/{id}")
	public Servico obterPorId(@PathVariable Integer id) {
		return servicoService.obterPorId(id);
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<Servico> alterarServico(@PathVariable Integer id,
			@Valid @RequestBody Servico servico) {
		Servico servicoAlterado = servicoService.alterar(id, servico);
		return ResponseEntity.ok(servicoAlterado);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> excluirServico(@PathVariable Integer id) {
		servicoService.excluir(id);
		return ResponseEntity.noContent().build();
	}
}