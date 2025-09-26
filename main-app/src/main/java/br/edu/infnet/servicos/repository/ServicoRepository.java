package br.edu.infnet.servicos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.infnet.servicos.model.domain.Servico;

@Repository
public interface ServicoRepository extends JpaRepository<Servico, Integer> {

}