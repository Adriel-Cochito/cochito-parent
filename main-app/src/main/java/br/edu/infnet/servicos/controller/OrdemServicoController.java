package br.edu.infnet.servicos.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.edu.infnet.servicos.dto.request.OrdemServicoRequestDTO;
import br.edu.infnet.servicos.dto.response.OrdemServicoResponseDTO;
import br.edu.infnet.servicos.model.domain.OrdemServico;
import br.edu.infnet.servicos.service.OrdemServicoService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/ordens-servico")
public class OrdemServicoController {

	private final OrdemServicoService ordemServicoService;

	public OrdemServicoController(OrdemServicoService ordemServicoService) {
		this.ordemServicoService = ordemServicoService;
	}

	// CRUD Básico

	@GetMapping
	public List<OrdemServico> obterOrdens() {
		return ordemServicoService.obterLista();
	}

	@GetMapping(value = "/{id}")
	public OrdemServico obterPorId(@PathVariable Integer id) {
		return ordemServicoService.obterPorId(id);
	}

	@PostMapping
	public ResponseEntity<OrdemServico> incluirOrdem(@Valid @RequestBody OrdemServicoRequestDTO ordemServicoRequest) {
		OrdemServico ordemSalva = ordemServicoService.criar(ordemServicoRequest);
		return ResponseEntity.status(HttpStatus.CREATED).body(ordemSalva);
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<OrdemServico> alterarOrdem(@PathVariable Integer id,
			@Valid @RequestBody OrdemServicoRequestDTO ordemServicoRequest) {
		OrdemServico ordemAlterada = ordemServicoService.alterar(id, ordemServicoRequest);
		return ResponseEntity.ok(ordemAlterada);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> excluirOrdem(@PathVariable Integer id) {
		ordemServicoService.excluir(id);
		return ResponseEntity.noContent().build();
	}

	// ORQUESTRAÇÃO: Endpoint que retorna dados enriquecidos com APIs externas
	@GetMapping("/{id}/detalhado")
	public ResponseEntity<OrdemServicoResponseDTO> buscarPorIdDetalhado(@PathVariable("id") Integer id) {
		OrdemServicoResponseDTO ordem = ordemServicoService.buscarPorIdComDistancia(id);
		return ResponseEntity.ok(ordem);
	}

	// Endpoints específicos usando Query Methods (Feature 4)

	@GetMapping("/status/{status}")
	public List<OrdemServico> buscarPorStatus(@PathVariable String status) {
		return ordemServicoService.buscarPorStatus(status);
	}

	@GetMapping("/cliente/{clienteId}/status/{status}")
	public List<OrdemServico> buscarPorClienteEStatus(@PathVariable Integer clienteId, 
			@PathVariable String status) {
		return ordemServicoService.buscarPorClienteEStatus(clienteId, status);
	}

	@GetMapping("/funcionario/{funcionarioId}/status/{status}")
	public List<OrdemServico> buscarPorFuncionarioEStatus(@PathVariable Integer funcionarioId, 
			@PathVariable String status) {
		return ordemServicoService.buscarPorFuncionarioEStatus(funcionarioId, status);
	}

	@GetMapping("/periodo")
	public List<OrdemServico> buscarPorPeriodo(
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fim) {
		return ordemServicoService.buscarPorPeriodo(inicio, fim);
	}

	@GetMapping("/servico")
	public List<OrdemServico> buscarPorServicoTitulo(@RequestParam String titulo) {
		return ordemServicoService.buscarPorServicoTitulo(titulo);
	}

	@GetMapping("/cliente")
	public List<OrdemServico> buscarPorClienteNome(@RequestParam String nome) {
		return ordemServicoService.buscarPorClienteNome(nome);
	}

	@GetMapping("/cpf/{cpf}")
	public List<OrdemServico> buscarPorClienteCpf(@PathVariable String cpf) {
		return ordemServicoService.buscarPorClienteCpf(cpf);
	}

	@GetMapping("/status/{status}/count")
	public Long contarPorStatus(@PathVariable String status) {
		return ordemServicoService.contarPorStatus(status);
	}

	@GetMapping("/pendentes")
	public List<OrdemServico> buscarPendentesPorPeriodo(
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fim) {
		return ordemServicoService.buscarPendentesPorPeriodo(inicio, fim);
	}
}