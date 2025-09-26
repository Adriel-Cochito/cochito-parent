package br.edu.infnet.servicos.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import br.edu.infnet.servicos.dto.request.EnderecoRequestDTO;
import br.edu.infnet.servicos.dto.request.FuncionarioRequestDTO;
import br.edu.infnet.servicos.dto.response.FuncionarioResponseDTO;
import br.edu.infnet.servicos.exceptions.RecursoInvalidoException;
import br.edu.infnet.servicos.exceptions.RecursoNaoEncontradoException;
import br.edu.infnet.servicos.model.domain.Endereco;
import br.edu.infnet.servicos.model.domain.Funcionario;
import br.edu.infnet.servicos.repository.FuncionarioRepository;
import jakarta.transaction.Transactional;

@Service
public class FuncionarioService implements CrudService<Funcionario, Integer> {

	private final FuncionarioRepository funcionarioRepository;

	public FuncionarioService(FuncionarioRepository funcionarioRepository) {
		this.funcionarioRepository = funcionarioRepository;
	}

	// Métodos do CrudService (mantidos como estão)
	@Override
	@Transactional
	public Funcionario incluir(Funcionario funcionario) {
		validar(funcionario);
		if (funcionario.getNome() == null) {
			throw new RecursoInvalidoException("O nome do funcionario é uma informação obrigatória!");
		}
		return funcionarioRepository.save(funcionario);
	}

	@Override
	@Transactional
	public void excluir(Integer id) {
		Funcionario funcionario = obterPorId(id);
		funcionarioRepository.delete(funcionario);
	}

	@Override
	public List<Funcionario> obterLista() {
		return funcionarioRepository.findAll();
	}

	@Override
	public Funcionario obterPorId(Integer id) {
		if (id == null || id <= 0) {
			throw new IllegalArgumentException("O ID para exclusão não pode ser nulo/zero!");
		}
		return funcionarioRepository.findById(id).orElseThrow(
				() -> new RecursoNaoEncontradoException("O funcionario com ID " + id + " não foi encontrado!"));
	}

	@Override
	@Transactional
	public Funcionario alterar(Integer id, Funcionario funcionario) {
		if (id == null || id == 0) {
			throw new IllegalArgumentException("O ID para alteração não pode ser nulo/zero!");
		}
		validar(funcionario);
		obterPorId(id);
		funcionario.setId(id);
		return funcionarioRepository.save(funcionario);
	}

	@Transactional
	public Funcionario inativar(Integer id) {
		if (id == null || id == 0) {
			throw new IllegalArgumentException("O ID para inativação não pode ser nulo/zero!");
		}
		Funcionario funcionarioExistente = obterPorId(id);
		if (!funcionarioExistente.isAtivo()) {
			System.out.println("Funcionario " + funcionarioExistente.getNome() + " já está inativo!");
			return funcionarioExistente;
		}
		funcionarioExistente.setAtivo(false);
		return funcionarioRepository.save(funcionarioExistente);
	}

	// NOVOS MÉTODOS COM DTOs
	@Transactional
	public FuncionarioResponseDTO criarComDTO(FuncionarioRequestDTO funcionarioRequest) {
		Funcionario funcionario = mapearParaEntidade(funcionarioRequest);
		Funcionario novoFuncionario = incluir(funcionario);
		return new FuncionarioResponseDTO(novoFuncionario);
	}

	public List<FuncionarioResponseDTO> listarTodosComDTO() {
		List<Funcionario> funcionarios = obterLista();
		return funcionarios.stream()
				.map(FuncionarioResponseDTO::new)
				.collect(Collectors.toList());
	}

	public FuncionarioResponseDTO buscarPorIdComDTO(Integer id) {
		Funcionario funcionario = obterPorId(id);
		return new FuncionarioResponseDTO(funcionario);
	}

	@Transactional
	public FuncionarioResponseDTO atualizarComDTO(Integer id, FuncionarioRequestDTO funcionarioRequest) {
		Funcionario funcionario = mapearParaEntidade(funcionarioRequest);
		Funcionario funcionarioAlterado = alterar(id, funcionario);
		return new FuncionarioResponseDTO(funcionarioAlterado);
	}

	@Transactional
	public FuncionarioResponseDTO inativarComDTO(Integer id) {
		Funcionario funcionarioInativo = inativar(id);
		return new FuncionarioResponseDTO(funcionarioInativo);
	}

	// MÉTODOS DE MAPEAMENTO (responsabilidade do service)
	private Funcionario mapearParaEntidade(FuncionarioRequestDTO dto) {
		Funcionario funcionario = new Funcionario();
		funcionario.setNome(dto.getNome());
		funcionario.setEmail(dto.getEmail());
		funcionario.setCpf(dto.getCpf());
		funcionario.setTelefone(dto.getTelefone());
		funcionario.setAtivo(dto.isAtivo());

		if (dto.getEndereco() != null) {
			funcionario.setEndereco(mapearEnderecoParaEntidade(dto.getEndereco()));
		}

		return funcionario;
	}

	private Endereco mapearEnderecoParaEntidade(EnderecoRequestDTO dto) {
		Endereco endereco = new Endereco();
		endereco.setCep(dto.getCep());
		endereco.setLogradouro(dto.getLogradouro());
		endereco.setComplemento(dto.getComplemento());
		endereco.setUnidade(dto.getUnidade());
		endereco.setBairro(dto.getBairro());
		endereco.setLocalidade(dto.getLocalidade());
		endereco.setUf(dto.getUf());
		endereco.setEstado(dto.getEstado());
		return endereco;
	}

	private void validar(Funcionario funcionario) {
		if (funcionario == null) {
			throw new IllegalArgumentException("Funcionário não pode ser nulo");
		}
		if (funcionario.getNome() == null || funcionario.getNome().trim().isEmpty()) {
			throw new RecursoInvalidoException("O nome do funcionário é uma informação obrigatória!");
		}
	}
}