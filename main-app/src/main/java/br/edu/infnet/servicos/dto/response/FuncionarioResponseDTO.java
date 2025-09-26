package br.edu.infnet.servicos.dto.response;

import br.edu.infnet.servicos.model.domain.Funcionario;

public class FuncionarioResponseDTO {
    
    private Integer id;
    private String nome;
    private String email;
    private String cpf;
    private String telefone;
    private boolean ativo;
    private EnderecoResponseDTO endereco;
    
    // Construtores
    public FuncionarioResponseDTO() {}
    
    public FuncionarioResponseDTO(Funcionario funcionario) {
        this.id = funcionario.getId();
        this.nome = funcionario.getNome();
        this.email = funcionario.getEmail();
        this.cpf = funcionario.getCpf();
        this.telefone = funcionario.getTelefone();
        this.ativo = funcionario.isAtivo();
        
        if (funcionario.getEndereco() != null) {
            this.endereco = new EnderecoResponseDTO(funcionario.getEndereco());
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
    
    
    public boolean isAtivo() {
        return ativo;
    }
    
    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
    
    public EnderecoResponseDTO getEndereco() {
        return endereco;
    }
    
    public void setEndereco(EnderecoResponseDTO endereco) {
        this.endereco = endereco;
    }
}