package com.fiap.restaurantes.service;

import com.fiap.restaurantes.entity.Cliente;
import com.fiap.restaurantes.entity.Endereco;
import com.fiap.restaurantes.repository.ClienteRepository;
import com.fiap.restaurantes.repository.EnderecoRepository;
import org.hibernate.mapping.Any;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClienteServiceTest {
    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private EnderecoRepository enderecoRepo;

    private EnderecoService enderecoService;

    private ClienteServiceImpl clienteService;

    AutoCloseable mock;

    @BeforeEach
    public void setUp() {
        mock = MockitoAnnotations.openMocks(this);

        enderecoService = new EnderecoServiceImpl(enderecoRepo);
        clienteService = new ClienteServiceImpl(clienteRepository, enderecoService);
    }

    @AfterEach
    void tearDown() throws Exception {
        mock.close();
    }

    @Test
    void testObterTodosClientes() {
        List<Cliente> clientes = new ArrayList<>();
        clientes.add(new Cliente(1L, "Cliente 1", "123456789", "cliente1@example.com", "12345678901", new Endereco()));
        clientes.add(new Cliente(2L, "Cliente 2", "987654321", "cliente2@example.com", "98765432101", new Endereco()));

        when(clienteRepository.findAll()).thenReturn(clientes);

        List<Cliente> resultado = clienteService.obterTodosClientes();

        verify(clienteRepository, times(1)).findAll();
        assertEquals(clientes.size(), resultado.size());
    }

    @Test
    void testObterClientePorId() {
        Cliente cliente = new Cliente(1L, "Cliente 1", "123456789", "cliente1@example.com", "12345678901", new Endereco());

        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));

        Optional<Cliente> resultado = clienteService.obterClientePorId(1L);

        verify(clienteRepository, times(1)).findById(1L);
        assertTrue(resultado.isPresent());
        assertEquals(cliente, resultado.get());
    }

    @Test
    void testCriarCliente() {
        Cliente cliente = new Cliente(1L, "Novo Cliente", "987654321", "novo_cliente@example.com", "98765432101", new Endereco());

        when(clienteRepository.save(cliente)).thenReturn(cliente);

        Cliente clienteCriado = clienteService.criarCliente(cliente);

        verify(clienteRepository, times(1)).save(cliente);
        assertEquals(cliente, clienteCriado);
    }

    @Test
    void testAtualizarClienteExistente() {

        var endereco = Endereco.builder()
                .id(1L)
                .rua("Rua AA")
                .bairro("Pinhais")
                .numero("121")
                .cep("12345-321")
                .cidade("Pinhais")
                .estado("PR")
                .pais("Brasil").build();

        Cliente clienteAtualizado = new Cliente(1L, "Cliente Atualizado", "987654321", "cliente_atualizado@example.com", "98765432101",
                endereco);

        when(clienteRepository.existsById(1L)).thenReturn(true);
        when(clienteRepository.save(clienteAtualizado)).thenReturn(clienteAtualizado);
        when(enderecoRepo.save(endereco)).thenAnswer(i -> i.getArgument(0));
        when(enderecoRepo.findById((any(Long.class)))).thenReturn(Optional.of(endereco));

        Optional<Cliente> clienteAtualizadoOpt = clienteService.atualizarCliente(1L, clienteAtualizado);

        verify(clienteRepository, times(1)).existsById(1L);
        verify(clienteRepository, times(1)).save(clienteAtualizado);
        assertTrue(clienteAtualizadoOpt.isPresent());
        assertEquals(clienteAtualizado, clienteAtualizadoOpt.get());
    }

    @Test
    void testAtualizarClienteNaoExistente() {
        Cliente cliente = new Cliente(1L, "Cliente Antigo", "123456789", "cliente_antigo@example.com", "12345678901", new Endereco());
        Cliente clienteAtualizado = new Cliente(1L, "Cliente Atualizado", "987654321", "cliente_atualizado@example.com", "98765432101", new Endereco());

        when(clienteRepository.existsById(1L)).thenReturn(false);

        Optional<Cliente> clienteAtualizadoOpt = clienteService.atualizarCliente(1L, clienteAtualizado);

        verify(clienteRepository, times(1)).existsById(1L);
        verify(clienteRepository, never()).save(any());
        assertTrue(clienteAtualizadoOpt.isEmpty());
    }

    @Test
    void testDeletarClienteExistente() {

        var endereco = Endereco.builder()
                .id(1L)
                .rua("Rua AA")
                .bairro("Pinhais")
                .numero("121")
                .cep("12345-321")
                .cidade("Pinhais")
                .estado("PR")
                .pais("Brasil").build();

        Cliente cliente = new Cliente(1L, "Cliente Atualizado", "987654321", "cliente_atualizado@example.com", "98765432101",
                endereco);

        when(clienteRepository.existsById(1L)).thenReturn(true);
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        when(enderecoRepo.existsById(1L)).thenReturn(true);

        boolean deletado = clienteService.deletarCliente(1L);

        verify(clienteRepository, times(1)).existsById(1L);
        verify(clienteRepository, times(1)).deleteById(1L);
        assertTrue(deletado);
    }

    @Test
    void testDeletarClienteNaoExistente() {
        when(clienteRepository.existsById(1L)).thenReturn(false);

        boolean deletado = clienteService.deletarCliente(1L);

        verify(clienteRepository, times(1)).existsById(1L);
        verify(clienteRepository, never()).deleteById(any());
        assertFalse(deletado);
    }

    @Test
    void testListarCliente() {
        List<Cliente> clientes = new ArrayList<>();
        clientes.add(new Cliente(1L, "Cliente 1", "123456789", "cliente@example.com", "12345678901", null));
        clientes.add(new Cliente(2L, "Cliente 2", "987654321", "cliente2@example.com", "98765432101", null));

        when(clienteRepository.findAll(Pageable.unpaged())).thenReturn(new PageImpl<>(clientes));

        Page<Cliente> resultado = clienteService.listar(Pageable.unpaged());

        assertEquals(clientes.size(), resultado.getContent().size());
    }

}
