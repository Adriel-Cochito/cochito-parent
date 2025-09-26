package br.edu.infnet.servicos.dto.request;

import jakarta.validation.constraints.NotNull;

public class ClienteRequestDTO {

    @NotNull(message = "ID do cliente é obrigatório")
    private Integer id;

    // Construtores
    public ClienteRequestDTO() {}

    public ClienteRequestDTO(Integer id) {
        this.id = id;
    }

    // Getters e Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
