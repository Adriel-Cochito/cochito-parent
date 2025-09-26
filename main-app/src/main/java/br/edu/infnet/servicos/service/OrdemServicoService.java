package br.edu.infnet.servicos.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import br.edu.infnet.servicos.dto.request.OrdemServicoRequestDTO;
import br.edu.infnet.servicos.dto.response.DistanciaResponseDTO;
import br.edu.infnet.servicos.dto.response.OrdemServicoResponseDTO;
import br.edu.infnet.servicos.exceptions.RecursoInvalidoException;
import br.edu.infnet.servicos.exceptions.RecursoNaoEncontradoException;
import br.edu.infnet.servicos.model.domain.Cliente;
import br.edu.infnet.servicos.model.domain.Funcionario;
import br.edu.infnet.servicos.model.domain.ItemServico;
import br.edu.infnet.servicos.model.domain.OrdemServico;
import br.edu.infnet.servicos.model.domain.Servico;
import br.edu.infnet.servicos.repository.OrdemServicoRepository;
import jakarta.transaction.Transactional;

@Service
public class OrdemServicoService implements CrudService<OrdemServico, Integer> {

	private final OrdemServicoRepository ordemServicoRepository;
	private final DistanciaService distanciaService;
	private final ClienteService clienteService;
	private final FuncionarioService funcionarioService;
	private final ServicoService servicoService;

	public OrdemServicoService(OrdemServicoRepository ordemServicoRepository, DistanciaService distanciaService,
			ClienteService clienteService, FuncionarioService funcionarioService, ServicoService servicoService) {
		this.ordemServicoRepository = ordemServicoRepository;
		this.distanciaService = distanciaService;
		this.clienteService = clienteService;
		this.funcionarioService = funcionarioService;
		this.servicoService = servicoService;
	}

	@Transactional
	public OrdemServico criar(OrdemServicoRequestDTO ordemServicoRequest) {
		OrdemServico ordemServico = converterParaEntidade(ordemServicoRequest);
		validarOrdemServico(ordemServico);

		if (ordemServico.getDataCriacao() == null) {
			ordemServico.setDataCriacao(LocalDateTime.now());
		}

		if (ordemServico.getStatus() == null || ordemServico.getStatus().trim().isEmpty()) {
			ordemServico.setStatus("PENDENTE");
		}

		return ordemServicoRepository.save(ordemServico);
	}

	@Override
	@Transactional
	public OrdemServico incluir(OrdemServico ordemServico) {
		validarOrdemServico(ordemServico);

		if (ordemServico.getDataCriacao() == null) {
			ordemServico.setDataCriacao(LocalDateTime.now());
		}

		if (ordemServico.getStatus() == null || ordemServico.getStatus().trim().isEmpty()) {
			ordemServico.setStatus("PENDENTE");
		}

		return ordemServicoRepository.save(ordemServico);
	}

	@Transactional
	public OrdemServico criar(OrdemServico ordemServico) {
		return incluir(ordemServico);
	}

	@Override
	@Transactional
	public OrdemServico alterar(Integer id, OrdemServico ordemServico) {
		buscarPorId(id); // Verifica se existe
		
		validarOrdemServico(ordemServico);
		ordemServico.setId(id);
		
		return ordemServicoRepository.save(ordemServico);
	}

	@Transactional
	public OrdemServico atualizar(Integer id, OrdemServico ordemServico) {
		return alterar(id, ordemServico);
	}

	@Override
	@Transactional
	public void excluir(Integer id) {
		OrdemServico ordemServico = buscarPorId(id);
		ordemServicoRepository.delete(ordemServico);
	}

	@Transactional
	public void deletar(Integer id) {
		excluir(id);
	}

	@Override
	public List<OrdemServico> obterLista() {
		return ordemServicoRepository.findAll();
	}

	public List<OrdemServico> listarTodos() {
		return obterLista();
	}

	@Override
	public OrdemServico obterPorId(Integer id) {
		if (id == null || id <= 0) {
			throw new IllegalArgumentException("ID inválido");
		}

		OrdemServico ordemServico = ordemServicoRepository.findById(id)
			.orElseThrow(() -> new RecursoNaoEncontradoException("Ordem de Serviço não encontrada"));

		// Consulta a distância entre o CEP do funcionário e o CEP do cliente
		if (ordemServico.getFuncionario() != null && ordemServico.getFuncionario().getEndereco() != null
				&& ordemServico.getCliente() != null && ordemServico.getCliente().getEndereco() != null) {

			String cepFuncionario = ordemServico.getFuncionario().getEndereco().getCep();
			String cepCliente = ordemServico.getCliente().getEndereco().getCep();

			try {
				DistanciaResponseDTO distancia = distanciaService.calcularDistancia(cepFuncionario, cepCliente);
				// Adiciona a informação de distância à ordem de serviço
				ordemServico.setDistancia(distancia.getDistanciaKm().toString() + " km");
			} catch (Exception e) {
				// Se houver erro na consulta de distância, continua sem a informação
				System.err.println("Erro ao consultar distância: " + e.getMessage());
			}
		}

		return ordemServico;
	}

	public OrdemServico buscarPorId(Integer id) {
		return obterPorId(id);
	}

	// ORQUESTRAÇÃO: Método que retorna DTO com dados das APIs externas
	public OrdemServicoResponseDTO buscarPorIdComDistancia(Integer id) {
		OrdemServico ordemServico = buscarPorId(id);
		
		DistanciaResponseDTO distancia = null;
		
		// Calcular distância se funcionário e cliente tiverem endereços
		if (ordemServico.getFuncionario() != null && 
			ordemServico.getFuncionario().getEndereco() != null &&
			ordemServico.getCliente() != null && 
			ordemServico.getCliente().getEndereco() != null) {

			try {
				String cepFuncionario = ordemServico.getFuncionario().getEndereco().getCep();
				String cepCliente = ordemServico.getCliente().getEndereco().getCep();

				distancia = distanciaService.calcularDistancia(cepFuncionario, cepCliente);
			} catch (Exception e) {
				// Log do erro, mas não falha a consulta
				System.err.println("Erro ao calcular distância: " + e.getMessage());
			}
		}

		return new OrdemServicoResponseDTO(ordemServico, distancia);
	}

	// Query methods específicos (mantidos)
	public List<OrdemServico> buscarPorStatus(String status) {
		return ordemServicoRepository.findByStatus(status);
	}

	public List<OrdemServico> buscarPorClienteEStatus(Integer clienteId, String status) {
		return ordemServicoRepository.findByClienteIdAndStatus(clienteId, status);
	}

	public List<OrdemServico> buscarPorFuncionarioEStatus(Integer funcionarioId, String status) {
		return ordemServicoRepository.findByFuncionarioIdAndStatus(funcionarioId, status);
	}

	// Métodos específicos usando Query Methods (Feature 4)

	public List<OrdemServico> buscarPorPeriodo(LocalDateTime dataInicio, LocalDateTime dataFim) {
		return ordemServicoRepository.findByDataCriacaoBetween(dataInicio, dataFim);
	}

	public List<OrdemServico> buscarPorServicoTitulo(String titulo) {
		return ordemServicoRepository.findByServicoTituloContainingIgnoreCase(titulo);
	}

	public List<OrdemServico> buscarPorClienteNome(String nome) {
		return ordemServicoRepository.findByClienteNomeContainingIgnoreCase(nome);
	}

	public List<OrdemServico> buscarPorClienteCpf(String cpf) {
		return ordemServicoRepository.findByClienteCpf(cpf);
	}

	public long contarPorStatus(String status) {
		return ordemServicoRepository.countByStatus(status);
	}

	public List<OrdemServico> buscarPendentesPorPeriodo(LocalDateTime inicio, LocalDateTime fim) {
		return ordemServicoRepository.findOrdensPendentesPorPeriodo(inicio, fim);
	}

	private void validarOrdemServico(OrdemServico ordemServico) {
		if (ordemServico == null) {
			throw new RecursoInvalidoException("Ordem de Serviço não pode ser nula");
		}

		if (ordemServico.getCliente() == null) {
			throw new RecursoInvalidoException("Cliente é obrigatório na Ordem de Serviço");
		}

		if (ordemServico.getFuncionario() == null) {
			throw new RecursoInvalidoException("Funcionário é obrigatório na Ordem de Serviço");
		}

		if (ordemServico.getItensServicos() == null || ordemServico.getItensServicos().isEmpty()) {
			throw new RecursoInvalidoException("Pelo menos um item de serviço é obrigatório");
		}
	}

	private OrdemServico converterParaEntidade(OrdemServicoRequestDTO request) {
		OrdemServico ordemServico = new OrdemServico();

		// Buscar e associar cliente
		Cliente cliente = clienteService.obterPorId(request.getCliente().getId());
		ordemServico.setCliente(cliente);

		// Buscar e associar funcionário
		Funcionario funcionario = funcionarioService.obterPorId(request.getFuncionario().getId());
		ordemServico.setFuncionario(funcionario);

		// Converter itens de serviço
		List<ItemServico> itensServicos = request.getItensServicos().stream()
			.map(itemRequest -> {
				ItemServico itemServico = new ItemServico();
				Servico servico = servicoService.obterPorId(itemRequest.getServicoId());
				itemServico.setServico(servico);
				itemServico.setQuantidade(itemRequest.getQuantidade());
				return itemServico;
			})
			.collect(java.util.stream.Collectors.toList());
		ordemServico.setItensServicos(itensServicos);

		// Definir outros campos
		ordemServico.setDataCriacao(request.getDataCriacao());
		ordemServico.setDataExecucao(request.getDataExecucao());
		ordemServico.setStatus(request.getStatus());

		return ordemServico;
	}
}