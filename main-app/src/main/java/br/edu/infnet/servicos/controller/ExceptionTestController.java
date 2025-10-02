package br.edu.infnet.servicos.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.infnet.servicos.exceptions.AuthenticationException;
import br.edu.infnet.servicos.exceptions.AccessDeniedException;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/test-exceptions")
@Tag(name = "7. Testes", description = "Endpoints para teste de exceções")
public class ExceptionTestController {

    @GetMapping("/auth")
    public String testAuthException() {
        throw new AuthenticationException("Teste de exceção de autenticação");
    }

    @GetMapping("/access")
    public String testAccessException() {
        throw new AccessDeniedException("Teste de exceção de acesso negado");
    }
}
