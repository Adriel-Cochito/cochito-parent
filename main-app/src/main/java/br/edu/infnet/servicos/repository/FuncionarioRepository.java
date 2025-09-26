package br.edu.infnet.servicos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.infnet.servicos.model.domain.Funcionario;
import java.util.Optional;

@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, Integer> {

    // Query Method para buscar por email (necessário para autenticação)
    Optional<Funcionario> findByEmail(String email);
}
