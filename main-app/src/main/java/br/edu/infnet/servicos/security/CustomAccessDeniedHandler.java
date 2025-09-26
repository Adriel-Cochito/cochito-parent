package br.edu.infnet.servicos.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Autowired
    private HandlerExceptionResolver handlerExceptionResolver;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                      AccessDeniedException accessDeniedException) throws IOException {
        
        // Converter para nossa exceção personalizada e delegar para o GlobalExceptionHandler
        br.edu.infnet.servicos.exceptions.AccessDeniedException customException = 
            new br.edu.infnet.servicos.exceptions.AccessDeniedException(
                "Acesso negado. Você não tem permissão para acessar este recurso"
            );
        
        handlerExceptionResolver.resolveException(request, response, null, customException);
    }
}
