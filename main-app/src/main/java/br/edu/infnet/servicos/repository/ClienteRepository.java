package br.edu.infnet.servicos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.infnet.servicos.model.domain.Cliente;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

    // Query Method para buscar por CPF (necessário para OrdemServicoLoader)
    Cliente findByCpf(String cpf);
    
    // Query Method para buscar por email (necessário para autenticação)
    Optional<Cliente> findByEmail(String email);
}