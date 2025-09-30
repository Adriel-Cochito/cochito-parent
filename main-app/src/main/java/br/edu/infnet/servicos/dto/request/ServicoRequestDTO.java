package br.edu.infnet.servicos.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

public class ServicoRequestDTO {
    @NotBlank(message = "Título é obrigatório")
    @Size(min = 3, max = 100, message = "Título deve ter entre 3 e 100 caracteres")
    private String titulo;

    @NotNull(message = "Preço é obrigatório")
    private BigDecimal preco;

    @NotBlank(message = "Descrição é obrigatória")
    @Size(min = 5, max = 255, message = "Descrição deve ter entre 5 e 255 caracteres")
    private String descricao;

    // Getters e Setters
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