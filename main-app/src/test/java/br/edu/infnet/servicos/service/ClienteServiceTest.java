package br.edu.infnet.servicos.service;

import br.edu.infnet.servicos.model.domain.Cliente;
import br.edu.infnet.servicos.repository.ClienteRepository;
import br.edu.infnet.servicos.exceptions.RecursoInvalidoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteService clienteService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void incluir_DeveSalvarCliente_QuandoValido() {
        Cliente cliente = new Cliente();
        cliente.setNome("João");
        cliente.setCpf("12345678901");
        cliente.setEmail("joao@email.com");
        cliente.setTelefone("11999999999");
        cliente.setFidelidade("OURO");
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

        Cliente salvo = clienteService.incluir(cliente);
        assertNotNull(salvo);
        assertEquals("João", salvo.getNome());
        verify(clienteRepository, times(1)).save(cliente);
    }

    @Test
    void incluir_DeveLancarExcecao_QuandoNomeNulo() {
        Cliente cliente = new Cliente();
        cliente.setCpf("12345678901");
        cliente.setEmail("joao@email.com");
        cliente.setTelefone("11999999999");
        cliente.setFidelidade("OURO");
        Exception ex = assertThrows(RecursoInvalidoException.class, () -> clienteService.incluir(cliente));
        assertTrue(ex.getMessage().toLowerCase().contains("nome"));
        verify(clienteRepository, never()).save(any());
    }

    @Test
    void obterPorId_DeveRetornarCliente_QuandoExiste() {
        Cliente cliente = new Cliente();
        cliente.setId(1);
        cliente.setNome("João");
        cliente.setCpf("12345678901");
        cliente.setEmail("joao@email.com");
        cliente.setTelefone("11999999999");
        cliente.setFidelidade("OURO");
        when(clienteRepository.findById(1)).thenReturn(Optional.of(cliente));
        Cliente resultado = clienteService.obterPorId(1);
        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
    }

    @Test
    void obterPorId_DeveLancarExcecao_QuandoNaoExiste() {
        when(clienteRepository.findById(99)).thenReturn(Optional.empty());
        assertThrows(Exception.class, () -> clienteService.obterPorId(99));
    }
}
