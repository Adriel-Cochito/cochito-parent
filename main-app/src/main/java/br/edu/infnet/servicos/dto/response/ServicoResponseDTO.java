package br.edu.infnet.servicos.dto.response;

import java.math.BigDecimal;

import br.edu.infnet.servicos.model.domain.Servico;

public class ServicoResponseDTO {
    
    private Integer id;
    private String titulo;
    private BigDecimal preco;
    private String descricao;
    
    // Construtores
    public ServicoResponseDTO() {}
    
    public ServicoResponseDTO(Servico servico) {
        this.id = servico.getId();
        this.titulo = servico.getTitulo();
        this.preco = servico.getPreco();
        this.descricao = servico.getDescricao();
    }
    
    // Getters e Setters
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getTitulo() {
        return titulo;
    }
    
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    
    public BigDecimal getPreco() {
        return preco;
    }
    
    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }
    
    public String getDescricao() {
        return descricao;
    }
    
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}