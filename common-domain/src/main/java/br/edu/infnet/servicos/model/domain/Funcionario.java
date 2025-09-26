package br.edu.infnet.servicos.model.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Funcionario extends Usuario {
	

	private boolean ativo;


	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "endereco_id")
	private Endereco endereco;


	@Override
	public String toString() {
		return String.format("Funcionario{%s, ativo=%s, endereco=%s}", super.toString(),
				ativo ? "ativo" : "inativo", endereco != null ? endereco.toString() : "null");
	}

	@Override
	public String obterTipo() {
		return "Funcionario";
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}


	

}