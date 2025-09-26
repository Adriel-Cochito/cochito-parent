package br.edu.infnet.servicos.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.edu.infnet.servicos.exceptions.RecursoInvalidoException;
import br.edu.infnet.servicos.exceptions.RecursoNaoEncontradoException;
import br.edu.infnet.servicos.model.domain.Servico;
import br.edu.infnet.servicos.repository.ServicoRepository;
import jakarta.transaction.Transactional;

@Service
public class ServicoService implements CrudService<Servico, Integer> {

    private final ServicoRepository servicoRepository;

    public ServicoService(ServicoRepository servicoRepository) {
        this.servicoRepository = servicoRepository;
    }

    @Override
    @Transactional
    public Servico incluir(Servico servico) {
        validar(servico);
        if (servico.getTitulo() == null) {
            throw new RecursoInvalidoException("O título do serviço é uma informação obrigatória!");
        }
        return servicoRepository.save(servico);
    }

    @Override
    @Transactional
    public void excluir(Integer id) {
        Servico servico = obterPorId(id);
        servicoRepository.delete(servico);
    }

    @Override
    public List<Servico> obterLista() {
        return servicoRepository.findAll();
    }

    @Override
    public Servico obterPorId(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("O ID para busca não pode ser nulo/zero!");
        }
        return servicoRepository.findById(id).orElseThrow(
                () -> new RecursoNaoEncontradoException("O serviço com ID " + id + " não foi encontrado!"));
    }

    @Override
    @Transactional
    public Servico alterar(Integer id, Servico servico) {
        if (id == null || id == 0) {
            throw new IllegalArgumentException("O ID para alteração não pode ser nulo/zero!");
        }
        validar(servico);
        obterPorId(id);
        servico.setId(id);
        return servicoRepository.save(servico);
    }

    public void validar(Servico servico) {
        if (servico == null) {
            throw new RecursoInvalidoException("Serviço não pode ser nulo!");
        }
        if (servico.getTitulo() == null || servico.getTitulo().trim().isEmpty()) {
            throw new RecursoInvalidoException("Título do serviço é obrigatório!");
        }
        if (servico.getPreco() == null) {
            throw new RecursoInvalidoException("Preço do serviço é obrigatório!");
        }
        if (servico.getPreco().compareTo(java.math.BigDecimal.ZERO) < 0) {
            throw new RecursoInvalidoException("Preço do serviço não pode ser negativo!");
        }
    }
}
