package com.fiap.restaurantes.service;

import com.fiap.restaurantes.entity.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ClienteService {

    List<Cliente> obterTodosClientes();

    Optional<Cliente> obterClientePorId(Long id);

    Cliente criarCliente(Cliente cliente);

    Optional<Cliente> atualizarCliente(Long id, Cliente cliente);

    boolean deletarCliente(Long id);

    Page<Cliente> listar(Pageable pageable);
}

