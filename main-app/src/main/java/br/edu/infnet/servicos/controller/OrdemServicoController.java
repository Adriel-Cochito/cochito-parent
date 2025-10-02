package br.edu.infnet.servicos.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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
import org.springframework.security.access.prepost.PreAuthorize;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.edu.infnet.servicos.dto.request.OrdemServicoRequestDTO;
import br.edu.infnet.servicos.dto.response.OrdemServicoResponseDTO;
import br.edu.infnet.servicos.model.domain.OrdemServico;
import br.edu.infnet.servicos.service.OrdemServicoService;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/ordens-servico")
@Tag(name = "4. Ordens de Serviço", description = "Gerenciamento de ordens de serviço")
public class OrdemServicoController {

	private static final Logger logger = LoggerFactory.getLogger(OrdemServicoController.class);

	private final OrdemServicoService ordemServicoService;

	public OrdemServicoController(OrdemServicoService ordemServicoService) {
		this.ordemServicoService = ordemServicoService;
	}

	// CRUD Básico

	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public List<OrdemServicoResponseDTO> obterOrdens() {
		logger.info("Listando todas as ordens de serviço");
		return ordemServicoService.obterLista().stream()
				.map(OrdemServicoResponseDTO::new)
				.collect(Collectors.toList());
	}

	@GetMapping(value = "/{id}")
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public OrdemServicoResponseDTO obterPorId(@PathVariable Integer id) {
		logger.info("Buscando ordem de serviço por id: {}", id);
		OrdemServico ordem = ordemServicoService.obterPorId(id);
		return new OrdemServicoResponseDTO(ordem);
	}

	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<OrdemServicoResponseDTO> incluirOrdem(@Valid @RequestBody OrdemServicoRequestDTO ordemServicoRequest) {
		logger.info("Recebida requisição para criar ordem de serviço");
		OrdemServico ordemSalva = ordemServicoService.criar(ordemServicoRequest);
		logger.info("Ordem de serviço criada com sucesso: id={}", ordemSalva.getId());
		return ResponseEntity.status(HttpStatus.CREATED).body(new OrdemServicoResponseDTO(ordemSalva));
	}

	@PutMapping(value = "/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<OrdemServicoResponseDTO> alterarOrdem(@PathVariable Integer id,
			@Valid @RequestBody OrdemServicoRequestDTO ordemServicoRequest) {
		logger.info("Alterando ordem de serviço id: {}", id);
		OrdemServico ordemAlterada = ordemServicoService.alterar(id, ordemServicoRequest);
		logger.info("Ordem de serviço alterada com sucesso: id={}", ordemAlterada.getId());
		return ResponseEntity.ok(new OrdemServicoResponseDTO(ordemAlterada));
	}

	@DeleteMapping(value = "/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Void> excluirOrdem(@PathVariable Integer id) {
		logger.info("Excluindo ordem de serviço id: {}", id);
		ordemServicoService.excluir(id);
		logger.info("Ordem de serviço excluída com sucesso: id={}", id);
		return ResponseEntity.noContent().build();
	}

	// ORQUESTRAÇÃO: Endpoint que retorna dados enriquecidos com APIs externas
	@GetMapping("/{id}/detalhado")
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public ResponseEntity<OrdemServicoResponseDTO> buscarPorIdDetalhado(@PathVariable("id") Integer id) {
		OrdemServicoResponseDTO ordem = ordemServicoService.buscarPorIdComDistancia(id);
		return ResponseEntity.ok(ordem);
	}

	// Endpoints específicos usando Query Methods (Feature 4)

	@GetMapping("/status/{status}")
	public List<OrdemServicoResponseDTO> buscarPorStatus(@PathVariable String status) {
		return ordemServicoService.buscarPorStatus(status).stream()
				.map(OrdemServicoResponseDTO::new)
				.collect(Collectors.toList());
	}

	@GetMapping("/cliente/{clienteId}/status/{status}")
	public List<OrdemServicoResponseDTO> buscarPorClienteEStatus(@PathVariable Integer clienteId, 
			@PathVariable String status) {
		return ordemServicoService.buscarPorClienteEStatus(clienteId, status).stream()
				.map(OrdemServicoResponseDTO::new)
				.collect(Collectors.toList());
	}

	@GetMapping("/funcionario/{funcionarioId}/status/{status}")
	public List<OrdemServicoResponseDTO> buscarPorFuncionarioEStatus(@PathVariable Integer funcionarioId, 
			@PathVariable String status) {
		return ordemServicoService.buscarPorFuncionarioEStatus(funcionarioId, status).stream()
				.map(OrdemServicoResponseDTO::new)
				.collect(Collectors.toList());
	}

	@GetMapping("/periodo")
	public List<OrdemServicoResponseDTO> buscarPorPeriodo(
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fim) {
		return ordemServicoService.buscarPorPeriodo(inicio, fim).stream()
				.map(OrdemServicoResponseDTO::new)
				.collect(Collectors.toList());
	}

	@GetMapping("/servico")
	public List<OrdemServicoResponseDTO> buscarPorServicoTitulo(@RequestParam String titulo) {
		return ordemServicoService.buscarPorServicoTitulo(titulo).stream()
				.map(OrdemServicoResponseDTO::new)
				.collect(Collectors.toList());
	}

	@GetMapping("/cliente")
	public List<OrdemServicoResponseDTO> buscarPorClienteNome(@RequestParam String nome) {
		return ordemServicoService.buscarPorClienteNome(nome).stream()
				.map(OrdemServicoResponseDTO::new)
				.collect(Collectors.toList());
	}

	@GetMapping("/cpf/{cpf}")
	public List<OrdemServicoResponseDTO> buscarPorClienteCpf(@PathVariable String cpf) {
		return ordemServicoService.buscarPorClienteCpf(cpf).stream()
				.map(OrdemServicoResponseDTO::new)
				.collect(Collectors.toList());
	}

	@GetMapping("/status/{status}/count")
	public Long contarPorStatus(@PathVariable String status) {
		return ordemServicoService.contarPorStatus(status);
	}

	@GetMapping("/pendentes")
	public List<OrdemServicoResponseDTO> buscarPendentesPorPeriodo(
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fim) {
		return ordemServicoService.buscarPendentesPorPeriodo(inicio, fim).stream()
				.map(OrdemServicoResponseDTO::new)
				.collect(Collectors.toList());
	}
}