package br.edu.infnet.servicos.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;

public class ItemServicoRequestDTO {

    @NotNull(message = "ID do serviço é obrigatório")
    private Integer servicoId;

    @Min(value = 1, message = "Quantidade deve ser maior que zero")
    private Integer quantidade = 1;

    // Construtores
    public ItemServicoRequestDTO() {}

    public ItemServicoRequestDTO(Integer servicoId, Integer quantidade) {
        this.servicoId = servicoId;
        this.quantidade = quantidade;
    }

    // Getters e Setters
    public Integer getServicoId() {
        return servicoId;
    }

    public void setServicoId(Integer servicoId) {
        this.servicoId = servicoId;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }
}
