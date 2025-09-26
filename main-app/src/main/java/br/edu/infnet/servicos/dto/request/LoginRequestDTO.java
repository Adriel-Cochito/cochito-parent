package br.edu.infnet.servicos.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class LoginRequestDTO {

    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email deve ter formato válido")
    private String email;

    @NotBlank(message = "Senha é obrigatória")
    private String password;

    // Construtores
    public LoginRequestDTO() {}

    public LoginRequestDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }

    // Getters e Setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "LoginRequestDTO{" +
               "email='" + email + '\'' +
               ", password='[PROTECTED]'" +
               '}';
    }
}
