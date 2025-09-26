package br.edu.infnet.servicos.dto.response;

import java.math.BigDecimal;

import br.edu.infnet.servicos.model.domain.ItemServico;

public class ItemServicoResponseDTO {
    
    private Integer id;
    private Integer quantidade;
    private BigDecimal subTotal;
    private ServicoResponseDTO servico;
    
    // Construtores
    public ItemServicoResponseDTO() {}
    
    public ItemServicoResponseDTO(ItemServico itemServico) {
        this.id = itemServico.getId();
        this.quantidade = itemServico.getQuantidade();
        this.subTotal = itemServico.calcularSubTotal(); // Método de cálculo da entidade
        
        if (itemServico.getServico() != null) {
            this.servico = new ServicoResponseDTO(itemServico.getServico());
        }
    }
    
    // Getters e Setters
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public Integer getQuantidade() {
        return quantidade;
    }
    
    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }
    
    public BigDecimal getSubTotal() {
        return subTotal;
    }
    
    public void setSubTotal(BigDecimal subTotal) {
        this.subTotal = subTotal;
    }
    
    public ServicoResponseDTO getServico() {
        return servico;
    }
    
    public void setServico(ServicoResponseDTO servico) {
        this.servico = servico;
    }
}