package br.edu.infnet.servicos.model.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    private RoleType type;

    // Construtores
    public Role() {}

    public Role(RoleType type) {
        this.type = type;
    }

    // Getters e Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public RoleType getType() {
        return type;
    }

    public void setType(RoleType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Role{" +
               "id=" + id +
               ", type=" + type +
               '}';
    }

    // Enum para os tipos de role
    public enum RoleType {
        ADMIN("ROLE_ADMIN"),
        USER("ROLE_USER");

        private final String authority;

        RoleType(String authority) {
            this.authority = authority;
        }

        public String getAuthority() {
            return authority;
        }
    }
}
