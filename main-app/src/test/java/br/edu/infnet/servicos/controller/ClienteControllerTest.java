package br.edu.infnet.servicos.controller;

import br.edu.infnet.servicos.model.domain.Cliente;
import br.edu.infnet.servicos.service.ClienteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ClienteControllerTest {

    @Mock
    private ClienteService clienteService;

    @InjectMocks
    private ClienteController clienteController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(clienteController).build();
    }

    @Test
    void obterClientes_DeveRetornarListaDeClientes() throws Exception {
        Cliente cliente1 = new Cliente();
        cliente1.setId(1);
        cliente1.setNome("João");
        Cliente cliente2 = new Cliente();
        cliente2.setId(2);
        cliente2.setNome("Maria");
        List<Cliente> clientes = Arrays.asList(cliente1, cliente2);
        when(clienteService.obterLista()).thenReturn(clientes);

        mockMvc.perform(get("/api/clientes").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].nome", is("João")))
                .andExpect(jsonPath("$[1].nome", is("Maria")));
    }

    @Test
    void obterPorId_DeveRetornarCliente_QuandoExiste() throws Exception {
        Cliente cliente = new Cliente();
        cliente.setId(1);
        cliente.setNome("João");
        when(clienteService.obterPorId(1)).thenReturn(cliente);

        mockMvc.perform(get("/api/clientes/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome", is("João")));
    }
}
