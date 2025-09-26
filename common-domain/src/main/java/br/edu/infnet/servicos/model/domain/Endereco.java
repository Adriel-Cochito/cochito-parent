package br.edu.infnet.servicos.model.domain;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Endereco {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
    private String cep; 

    
    private String logradouro; 

    private String complemento;

    private String unidade;

    private String bairro; 

    private String localidade; 

    private String uf; 

    private String estado;
	
	//TODO construtor do endereço
	
	@Override
	public String toString() {
		 return "Endereco{" +
	               "id=" + id +
	               ", cep='" + cep + '\'' +
	               ", logradouro='" + logradouro + '\'' +
	               ", complemento='" + complemento + '\'' +
	               ", unidade='" + unidade + '\'' +
	               ", bairro='" + bairro + '\'' +
	               ", localidade='" + localidade + '\'' +
	               ", uf='" + uf + '\'' +
	               ", estado='" + estado + '\'' +
	               '}';
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

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