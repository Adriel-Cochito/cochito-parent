package br.edu.infnet.servicos.dto.response;

import java.util.List;

public class LoginResponseDTO {

    private boolean success;
    private String message;
    private String token;
    private String tokenType = "Bearer";
    private UserInfoDTO user;

    // Construtores
    public LoginResponseDTO() {}

    public LoginResponseDTO(boolean success, String message, String token, UserInfoDTO user) {
        this.success = success;
        this.message = message;
        this.token = token;
        this.user = user;
    }

    // Getters e Setters
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public UserInfoDTO getUser() {
        return user;
    }

    public void setUser(UserInfoDTO user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    // Classe interna para informações do usuário
    public static class UserInfoDTO {
        private Integer id;
        private String nome;
        private String email;
        private String tipo;
        private List<String> roles;

        public UserInfoDTO() {}

        public UserInfoDTO(Integer id, String nome, String email, String tipo, List<String> roles) {
            this.id = id;
            this.nome = nome;
            this.email = email;
            this.tipo = tipo;
            this.roles = roles;
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

        public String getTipo() {
            return tipo;
        }

        public void setTipo(String tipo) {
            this.tipo = tipo;
        }

        public List<String> getRoles() {
            return roles;
        }

        public void setRoles(List<String> roles) {
            this.roles = roles;
        }
    }
}
