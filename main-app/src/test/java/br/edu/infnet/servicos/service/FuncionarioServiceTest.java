package br.edu.infnet.servicos.service;

import br.edu.infnet.servicos.model.domain.Funcionario;
import br.edu.infnet.servicos.repository.FuncionarioRepository;
import br.edu.infnet.servicos.exceptions.RecursoInvalidoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FuncionarioServiceTest {

    @Mock
    private FuncionarioRepository funcionarioRepository;

    @InjectMocks
    private FuncionarioService funcionarioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void incluir_DeveSalvarFuncionario_QuandoValido() {
        Funcionario funcionario = new Funcionario();
        funcionario.setNome("Maria");
        funcionario.setCpf("12345678901");
        funcionario.setEmail("maria@email.com");
        funcionario.setTelefone("11999999999");
        funcionario.setAtivo(true);
        when(funcionarioRepository.save(any(Funcionario.class))).thenReturn(funcionario);

        Funcionario salvo = funcionarioService.incluir(funcionario);
        assertNotNull(salvo);
        assertEquals("Maria", salvo.getNome());
        verify(funcionarioRepository, times(1)).save(funcionario);
    }

    @Test
    void incluir_DeveLancarExcecao_QuandoNomeNulo() {
        Funcionario funcionario = new Funcionario();
        funcionario.setCpf("12345678901");
        funcionario.setEmail("maria@email.com");
        funcionario.setTelefone("11999999999");
        funcionario.setAtivo(true);
        Exception ex = assertThrows(RecursoInvalidoException.class, () -> funcionarioService.incluir(funcionario));
        assertTrue(ex.getMessage().toLowerCase().contains("nome"));
        verify(funcionarioRepository, never()).save(any());
    }

    @Test
    void obterPorId_DeveRetornarFuncionario_QuandoExiste() {
        Funcionario funcionario = new Funcionario();
        funcionario.setId(1);
        funcionario.setNome("Maria");
        funcionario.setCpf("12345678901");
        funcionario.setEmail("maria@email.com");
        funcionario.setTelefone("11999999999");
        funcionario.setAtivo(true);
        when(funcionarioRepository.findById(1)).thenReturn(Optional.of(funcionario));
        Funcionario resultado = funcionarioService.obterPorId(1);
        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
    }

    @Test
    void obterPorId_DeveLancarExcecao_QuandoNaoExiste() {
        when(funcionarioRepository.findById(99)).thenReturn(Optional.empty());
        assertThrows(Exception.class, () -> funcionarioService.obterPorId(99));
    }
}
