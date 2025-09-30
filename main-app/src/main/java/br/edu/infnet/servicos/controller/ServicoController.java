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
import org.springframework.security.access.prepost.PreAuthorize;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.edu.infnet.servicos.model.domain.Servico;
import br.edu.infnet.servicos.service.ServicoService;
import br.edu.infnet.servicos.dto.request.ServicoRequestDTO;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/servicos")
public class ServicoController {

	private static final Logger logger = LoggerFactory.getLogger(ServicoController.class);

    private final ServicoService servicoService;
	
	public ServicoController(ServicoService servicoService) {
		this.servicoService = servicoService;
	}

	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Servico> incluirServico(@Valid @RequestBody ServicoRequestDTO dto) {
		logger.info("Recebida requisição para criar serviço: {}", dto.getTitulo());
		Servico servico = new Servico();
		servico.setTitulo(dto.getTitulo());
		servico.setPreco(dto.getPreco());
		servico.setDescricao(dto.getDescricao());
		Servico servicoSalvo = servicoService.incluir(servico);
		logger.info("Serviço criado com sucesso: id={}, titulo={}", servicoSalvo.getId(), servicoSalvo.getTitulo());
		return ResponseEntity.status(HttpStatus.CREATED).body(servicoSalvo);
	}
	
	@GetMapping
	public List<Servico> obterServico() {
		logger.info("Listando todos os serviços");
		return servicoService.obterLista();
	}

	@GetMapping(value = "/{id}")
	public Servico obterPorId(@PathVariable Integer id) {
		logger.info("Buscando serviço por id: {}", id);
		return servicoService.obterPorId(id);
	}

	@PutMapping(value = "/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Servico> alterarServico(@PathVariable Integer id,
			@Valid @RequestBody Servico servico) {
		logger.info("Alterando serviço id: {}", id);
		Servico servicoAlterado = servicoService.alterar(id, servico);
		logger.info("Serviço alterado com sucesso: id={}, titulo={}", servicoAlterado.getId(), servicoAlterado.getTitulo());
		return ResponseEntity.ok(servicoAlterado);
	}

	@DeleteMapping(value = "/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Void> excluirServico(@PathVariable Integer id) {
		logger.info("Excluindo serviço id: {}", id);
		servicoService.excluir(id);
		logger.info("Serviço excluído com sucesso: id={}", id);
		return ResponseEntity.noContent().build();
	}
}