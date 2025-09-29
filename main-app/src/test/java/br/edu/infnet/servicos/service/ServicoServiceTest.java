package br.edu.infnet.servicos.service;

import br.edu.infnet.servicos.model.domain.Servico;
import br.edu.infnet.servicos.repository.ServicoRepository;
import br.edu.infnet.servicos.exceptions.RecursoInvalidoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ServicoServiceTest {

    @Mock
    private ServicoRepository servicoRepository;

    @InjectMocks
    private ServicoService servicoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void incluir_DeveSalvarServico_QuandoValido() {
        Servico servico = new Servico();
        servico.setTitulo("Limpeza");
        servico.setPreco(java.math.BigDecimal.valueOf(100.0));
        servico.setDescricao("Limpeza completa");
        when(servicoRepository.save(any(Servico.class))).thenReturn(servico);

        Servico salvo = servicoService.incluir(servico);
        assertNotNull(salvo);
        assertEquals("Limpeza", salvo.getTitulo());
        verify(servicoRepository, times(1)).save(servico);
    }

    @Test
    void incluir_DeveLancarExcecao_QuandoTituloNulo() {
        Servico servico = new Servico();
        servico.setPreco(java.math.BigDecimal.valueOf(100.0));
        servico.setDescricao("Limpeza completa");
        Exception ex = assertThrows(RecursoInvalidoException.class, () -> servicoService.incluir(servico));
        assertTrue(ex.getMessage().toLowerCase().contains("título"));
        verify(servicoRepository, never()).save(any());
    }

    @Test
    void obterPorId_DeveRetornarServico_QuandoExiste() {
        Servico servico = new Servico();
        servico.setId(1);
        servico.setTitulo("Limpeza");
        servico.setPreco(java.math.BigDecimal.valueOf(100.0));
        servico.setDescricao("Limpeza completa");
        when(servicoRepository.findById(1)).thenReturn(Optional.of(servico));
        Servico resultado = servicoService.obterPorId(1);
        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
    }

    @Test
    void obterPorId_DeveLancarExcecao_QuandoNaoExiste() {
        when(servicoRepository.findById(99)).thenReturn(Optional.empty());
        assertThrows(Exception.class, () -> servicoService.obterPorId(99));
    }
}
