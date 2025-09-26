package br.edu.infnet.servicos.dto.request;

import jakarta.validation.constraints.NotNull;

public class FuncionarioIdRequestDTO {

    @NotNull(message = "ID do funcionário é obrigatório")
    private Integer id;

    // Construtores
    public FuncionarioIdRequestDTO() {}

    public FuncionarioIdRequestDTO(Integer id) {
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