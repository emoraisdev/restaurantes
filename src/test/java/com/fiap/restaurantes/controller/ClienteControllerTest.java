package com.fiap.restaurantes.controller;

import com.fiap.restaurantes.entity.Cliente;
import com.fiap.restaurantes.service.ClienteService;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class ClienteControllerTest {

    @Mock
    private ClienteService clienteService;

    @InjectMocks
    private ClienteController clienteController;

    @BeforeEach
    void setUp() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Nested
    class ObterTodosClientes {

        @Test
        void deveRetornarListaDeClientes() {

            List<Cliente> clientes = new ArrayList<>();
            clientes.add(new Cliente());
            clientes.add(new Cliente());
            when(clienteService.obterTodosClientes()).thenReturn(clientes);

            ResponseEntity<List<Cliente>> response = clienteController.obterTodosClientes();

            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(clientes, response.getBody());
            verify(clienteService, times(1)).obterTodosClientes();
        }
    @Nested
    class ObterClientePorId {

        @Test
        void deveRetornarClienteExistente() {

            Long id = 1L;
            Cliente cliente = new Cliente();
            cliente.setId(id);
            when(clienteService.obterClientePorId(id)).thenReturn(Optional.of(cliente));

            ResponseEntity<Cliente> responseEntity = clienteController.obterClientePorId(id);

            assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
            assertEquals(cliente, responseEntity.getBody());
        }

        @Test
        void deveRetornarNotFoundParaClienteInexistente() {

            Long id = 1L;
            when(clienteService.obterClientePorId(id)).thenReturn(Optional.empty());

            ResponseEntity<Cliente> responseEntity = clienteController.obterClientePorId(id);

            assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        }
    }

    @Nested
    class CriarCliente {

        @Test
        void deveRetornarNovoClienteCriado() {

            Cliente cliente = new Cliente();
            when(clienteService.criarCliente(cliente)).thenReturn(cliente);

            ResponseEntity<Cliente> responseEntity = clienteController.criarCliente(cliente);

            assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
            assertEquals(cliente, responseEntity.getBody());
        }
    }

    @Nested
    class AtualizarCliente {

        @Test
        void deveRetornarClienteAtualizado() {

            Long id = 1L;
            Cliente cliente = new Cliente();
            cliente.setId(id);
            when(clienteService.atualizarCliente(id, cliente)).thenReturn(Optional.of(cliente));

            ResponseEntity<Cliente> responseEntity = clienteController.atualizarCliente(id, cliente);

            assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
            assertEquals(cliente, responseEntity.getBody());
        }

        @Test
        void deveRetornarNotFoundParaClienteInexistente() {

            Long id = 1L;
            Cliente cliente = new Cliente();
            when(clienteService.atualizarCliente(id, cliente)).thenReturn(Optional.empty());

            ResponseEntity<Cliente> responseEntity = clienteController.atualizarCliente(id, cliente);

            assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        }
    }

    @Nested
    class DeletarCliente {

        @Test
        void deveRetornarNoContentParaClienteDeletado() {

            Long id = 1L;
            when(clienteService.deletarCliente(id)).thenReturn(true);

            ResponseEntity<Void> responseEntity = clienteController.deletarCliente(id);

            assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        }

        @Test
        void deveRetornarNotFoundParaClienteNaoDeletado() {

            Long id = 1L;
            when(clienteService.deletarCliente(id)).thenReturn(false);

            ResponseEntity<Void> responseEntity = clienteController.deletarCliente(id);

            assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        }
    }

    @Nested
    class ListarClientes {

        @Test
        void deveRetornarPaginaDeClientes() {

            List<Cliente> clientes = new ArrayList<>();
            clientes.add(new Cliente());
            clientes.add(new Cliente());
            Page<Cliente> paginaClientes = new PageImpl<>(clientes);

            PageRequest pageable = PageRequest.of(0, 10);

            when(clienteService.listar(pageable)).thenReturn(paginaClientes);

            ResponseEntity<Page<Cliente>> response = clienteController.listarClientes(pageable);

            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(paginaClientes, response.getBody());
            verify(clienteService, times(1)).listar(pageable);
        }
    }}}

