package br.edu.infnet.servicos.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.infnet.servicos.exceptions.AuthenticationException;
import br.edu.infnet.servicos.exceptions.AccessDeniedException;

@RestController
@RequestMapping("/api/test-exceptions")
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
