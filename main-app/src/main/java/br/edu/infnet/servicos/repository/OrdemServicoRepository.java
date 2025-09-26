package br.edu.infnet.servicos.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.edu.infnet.servicos.model.domain.OrdemServico;

@Repository
public interface OrdemServicoRepository extends JpaRepository<OrdemServico, Integer> {

	List<OrdemServico> findByStatus(String status);

	List<OrdemServico> findByClienteIdAndStatus(Integer clienteId, String status);

	List<OrdemServico> findByFuncionarioIdAndStatus(Integer funcionarioId, String status);

	List<OrdemServico> findByDataCriacaoBetween(LocalDateTime dataInicio, LocalDateTime dataFim);

	@Query("SELECT DISTINCT os FROM OrdemServico os JOIN os.itensServicos item JOIN item.servico s WHERE s.titulo LIKE %:titulo%")
	List<OrdemServico> findByServicoTituloContainingIgnoreCase(@Param("titulo") String titulo);

	@Query("SELECT os FROM OrdemServico os WHERE os.cliente.nome LIKE %:nome%")
	List<OrdemServico> findByClienteNomeContainingIgnoreCase(@Param("nome") String nome);

	@Query("SELECT os FROM OrdemServico os WHERE os.cliente.cpf = :cpf")
	List<OrdemServico> findByClienteCpf(@Param("cpf") String cpf);

	@Query("SELECT COUNT(os) FROM OrdemServico os WHERE os.status = :status")
	long countByStatus(@Param("status") String status);

	@Query("SELECT os FROM OrdemServico os WHERE os.status = 'PENDENTE' AND os.dataCriacao BETWEEN :inicio AND :fim")
	List<OrdemServico> findOrdensPendentesPorPeriodo(@Param("inicio") LocalDateTime inicio,
			@Param("fim") LocalDateTime fim);
}