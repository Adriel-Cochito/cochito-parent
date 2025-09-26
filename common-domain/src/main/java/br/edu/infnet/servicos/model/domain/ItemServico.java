package br.edu.infnet.servicos.model.domain;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class ItemServico {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "servico_id")
	private Servico servico;

	private Integer quantidade;

	public BigDecimal calcularSubTotal() {
		// Validação consolidada de quantidade
		if (!isQuantidadeValida()) {
			return BigDecimal.ZERO;
		}

		// Validação consolidada de serviço e preço
		if (!isServicoValido()) {
			return BigDecimal.ZERO;
		}

		return servico.getPreco().multiply(new BigDecimal(quantidade));
	}

	private boolean isQuantidadeValida() {
		return quantidade != null && quantidade > 0;
	}
	
	private boolean isServicoValido() {
		return servico != null && servico.getPreco() != null;
	}

	public Servico getServico() {
		return servico;
	}

	public void setServico(Servico servico) {
		this.servico = servico;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	

}
