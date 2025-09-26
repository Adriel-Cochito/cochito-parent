package br.edu.infnet.servicos.dto.response;

import br.edu.infnet.servicos.model.domain.Cliente;

public class ClienteResponseDTO {
    
    private Integer id;
    private String nome;
    private String email;
    private String cpf;
    private String telefone;
    private String fidelidade;
    private EnderecoResponseDTO endereco;
    
    // Construtores
    public ClienteResponseDTO() {}
    
    public ClienteResponseDTO(Cliente cliente) {
        this.id = cliente.getId();
        this.nome = cliente.getNome();
        this.email = cliente.getEmail();
        this.cpf = cliente.getCpf();
        this.telefone = cliente.getTelefone();
        this.fidelidade = cliente.getFidelidade();
        
        if (cliente.getEndereco() != null) {
            this.endereco = new EnderecoResponseDTO(cliente.getEndereco());
        }
    }
    
    // Getters e Setters
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getNome() {
        return nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getCpf() {
        return cpf;
    }
    
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    
    public String getTelefone() {
        return telefone;
    }
    
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
    
    public String getFidelidade() {
        return fidelidade;
    }
    
    public void setFidelidade(String fidelidade) {
        this.fidelidade = fidelidade;
    }
    
    public EnderecoResponseDTO getEndereco() {
        return endereco;
    }
    
    public void setEndereco(EnderecoResponseDTO endereco) {
        this.endereco = endereco;
    }
}