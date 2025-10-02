package br.edu.infnet.servicos.dto.request;

import jakarta.validation.constraints.NotBlank;

public class ClienteFidelidadeRequestDTO {

    @NotBlank(message = "Fidelidade é obrigatória")
    private String fidelidade;

    // Construtores
    public ClienteFidelidadeRequestDTO() {}

    public ClienteFidelidadeRequestDTO(String fidelidade) {
        this.fidelidade = fidelidade;
    }

    // Getters e Setters
    public String getFidelidade() {
        return fidelidade;
    }

    public void setFidelidade(String fidelidade) {
        this.fidelidade = fidelidade;
    }
}

