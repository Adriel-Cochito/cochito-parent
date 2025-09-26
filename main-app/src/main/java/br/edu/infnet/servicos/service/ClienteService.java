package br.edu.infnet.servicos.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.edu.infnet.servicos.exceptions.RecursoInvalidoException;
import br.edu.infnet.servicos.exceptions.RecursoNaoEncontradoException;
import br.edu.infnet.servicos.exceptions.ViolacaoIntegridadeReferencialException;
import br.edu.infnet.servicos.model.domain.Cliente;
import br.edu.infnet.servicos.repository.ClienteRepository;
import jakarta.transaction.Transactional;

@Service
public class ClienteService implements CrudService<Cliente, Integer> {

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Override
    @Transactional
    public Cliente incluir(Cliente cliente) {
        validar(cliente);
        if (cliente.getNome() == null) {
            throw new RecursoInvalidoException("O nome do cliente é uma informação obrigatória!");
        }
        return clienteRepository.save(cliente);
    }

    @Override
    @Transactional
    public void excluir(Integer id) {
        try {
            Cliente cliente = obterPorId(id);
            clienteRepository.delete(cliente);
        } catch (org.springframework.dao.DataIntegrityViolationException ex) {
            throw new ViolacaoIntegridadeReferencialException(
                "Não é possível excluir o cliente pois ele possui ordens de serviço associadas. " +
                "Remova primeiro as ordens de serviço antes de tentar excluir o cliente.", ex);
        }
    }

    @Override
    public List<Cliente> obterLista() {
        return clienteRepository.findAll();
    }

    @Override
    public Cliente obterPorId(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("O ID para busca não pode ser nulo/zero!");
        }
        return clienteRepository.findById(id).orElseThrow(
                () -> new RecursoNaoEncontradoException("O cliente com ID " + id + " não foi encontrado!"));
    }

    @Override
    @Transactional
    public Cliente alterar(Integer id, Cliente cliente) {
        if (id == null || id == 0) {
            throw new IllegalArgumentException("O ID para alteração não pode ser nulo/zero!");
        }
        validar(cliente);
        obterPorId(id);
        cliente.setId(id);
        return clienteRepository.save(cliente);
    }
    
	@Transactional
	public Cliente atualizarFidelidade(Integer id, String novaFidelidade) {
		if (novaFidelidade == null || novaFidelidade.trim().isEmpty()) {
			throw new RecursoInvalidoException("Fidelidade não pode ser vazia");
		}
		
		Cliente clienteExistente = obterPorId(id);
		clienteExistente.setFidelidade(novaFidelidade);

		return clienteRepository.save(clienteExistente);
	}

    public Cliente buscarPorCpf(String cpf) {
        if (cpf == null || cpf.trim().isEmpty()) {
            throw new IllegalArgumentException("CPF não pode ser nulo ou vazio!");
        }
        Cliente cliente = clienteRepository.findByCpf(cpf);
        if (cliente == null) {
            throw new RecursoNaoEncontradoException("Cliente com CPF " + cpf + " não encontrado!");
        }
        return cliente;
    }

    public void validar(Cliente cliente) {
        if (cliente == null) {
            throw new RecursoInvalidoException("Cliente não pode ser nulo!");
        }
        if (cliente.getNome() == null || cliente.getNome().trim().isEmpty()) {
            throw new RecursoInvalidoException("Nome do cliente é obrigatório!");
        }
        if (cliente.getCpf() == null || cliente.getCpf().trim().isEmpty()) {
            throw new RecursoInvalidoException("CPF do cliente é obrigatório!");
        }
        if (cliente.getEmail() == null || cliente.getEmail().trim().isEmpty()) {
            throw new RecursoInvalidoException("Email do cliente é obrigatório!");
        }
    }
}
