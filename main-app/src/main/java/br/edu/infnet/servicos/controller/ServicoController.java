package br.edu.infnet.servicos.controller;

import java.util.List;
import java.util.stream.Collectors;

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
import br.edu.infnet.servicos.dto.response.ServicoResponseDTO;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/servicos")
@Tag(name = "3. Serviços", description = "Gerenciamento de serviços")
public class ServicoController {

	private static final Logger logger = LoggerFactory.getLogger(ServicoController.class);

    private final ServicoService servicoService;
	
	public ServicoController(ServicoService servicoService) {
		this.servicoService = servicoService;
	}

	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ServicoResponseDTO> incluirServico(@Valid @RequestBody ServicoRequestDTO dto) {
		logger.info("Recebida requisição para criar serviço: {}", dto.getTitulo());
		Servico servico = convertToEntity(dto);
		Servico servicoSalvo = servicoService.incluir(servico);
		logger.info("Serviço criado com sucesso: id={}, titulo={}", servicoSalvo.getId(), servicoSalvo.getTitulo());
		return ResponseEntity.status(HttpStatus.CREATED).body(new ServicoResponseDTO(servicoSalvo));
	}
	
	@GetMapping
	public List<ServicoResponseDTO> obterServico() {
		logger.info("Listando todos os serviços");
		return servicoService.obterLista().stream()
				.map(ServicoResponseDTO::new)
				.collect(Collectors.toList());
	}

	@GetMapping(value = "/{id}")
	public ServicoResponseDTO obterPorId(@PathVariable Integer id) {
		logger.info("Buscando serviço por id: {}", id);
		Servico servico = servicoService.obterPorId(id);
		return new ServicoResponseDTO(servico);
	}

	@PutMapping(value = "/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ServicoResponseDTO> alterarServico(@PathVariable Integer id,
			@Valid @RequestBody ServicoRequestDTO dto) {
		logger.info("Alterando serviço id: {}", id);
		Servico servico = convertToEntity(dto);
		Servico servicoAlterado = servicoService.alterar(id, servico);
		logger.info("Serviço alterado com sucesso: id={}, titulo={}", servicoAlterado.getId(), servicoAlterado.getTitulo());
		return ResponseEntity.ok(new ServicoResponseDTO(servicoAlterado));
	}

	@DeleteMapping(value = "/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Void> excluirServico(@PathVariable Integer id) {
		logger.info("Excluindo serviço id: {}", id);
		servicoService.excluir(id);
		logger.info("Serviço excluído com sucesso: id={}", id);
		return ResponseEntity.noContent().build();
	}

	// Método helper para converter DTO para entidade
	private Servico convertToEntity(ServicoRequestDTO dto) {
		Servico servico = new Servico();
		servico.setTitulo(dto.getTitulo());
		servico.setPreco(dto.getPreco());
		servico.setDescricao(dto.getDescricao());
		return servico;
	}
}