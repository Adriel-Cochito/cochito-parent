package br.edu.infnet.servicos.dto.request;

import jakarta.validation.constraints.*;

public class EnderecoRequestDTO {

	@NotBlank(message = "CEP é obrigatório")
	@Pattern(regexp = "\\d{5}-\\d{3}", message = "CEP deve estar no formato XXXXX-XXX")
	private String cep;

	@NotBlank(message = "Logradouro é obrigatório")
	@Size(min = 3, max = 100, message = "Logradouro deve ter entre 3 e 100 caracteres")
	private String logradouro;

	private String complemento;

	private String unidade;

	@NotBlank(message = "Bairro é obrigatório")
	@Size(min = 3, max = 50, message = "Bairro deve ter entre 3 e 50 caracteres")
	private String bairro;

	@NotBlank(message = "Localidade é obrigatória")
	@Size(min = 3, max = 50, message = "Localidade deve ter entre 3 e 50 caracteres")
	private String localidade;

	@NotBlank(message = "UF é obrigatória")
	@Size(min = 2, max = 2, message = "UF deve ter 2 caracteres")
	private String uf;

	@NotBlank(message = "Estado é obrigatório")
	@Size(min = 3, max = 50, message = "Estado deve ter entre 3 e 50 caracteres")
	private String estado;

	// Construtores
	public EnderecoRequestDTO() {
	}

	// Getters e Setters
	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getUnidade() {
		return unidade;
	}

	public void setUnidade(String unidade) {
		this.unidade = unidade;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getLocalidade() {
		return localidade;
	}

	public void setLocalidade(String localidade) {
		this.localidade = localidade;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
}