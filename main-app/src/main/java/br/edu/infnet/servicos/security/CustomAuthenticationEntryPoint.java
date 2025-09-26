package br.edu.infnet.servicos.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Autowired
    private HandlerExceptionResolver handlerExceptionResolver;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                        AuthenticationException authException) throws IOException {
        
        // Converter para nossa exceção personalizada e delegar para o GlobalExceptionHandler
        br.edu.infnet.servicos.exceptions.AuthenticationException customException = 
            new br.edu.infnet.servicos.exceptions.AuthenticationException(
                "Token JWT inválido, ausente ou expirado"
            );
        
        handlerExceptionResolver.resolveException(request, response, null, customException);
    }
}
