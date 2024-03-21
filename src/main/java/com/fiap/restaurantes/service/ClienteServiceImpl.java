package com.fiap.restaurantes.service;

import com.fiap.restaurantes.entity.Cliente;
import com.fiap.restaurantes.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;

    @Override
    public List<Cliente> obterTodosClientes() {
        return clienteRepository.findAll();
    }

    @Override
    public Optional<Cliente> obterClientePorId(Long id) {
        return clienteRepository.findById(id);
    }

    @Override
    public Cliente criarCliente(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    @Override
    public Optional<Cliente> atualizarCliente(Long id, Cliente cliente) {
        if (clienteRepository.existsById(id)) {
            cliente.setId(id);
            return Optional.of(clienteRepository.save(cliente));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public boolean deletarCliente(Long id) {
        if (clienteRepository.existsById(id)) {
            clienteRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Page<Cliente> listar(Pageable pageable) {
        return clienteRepository.findAll(pageable);
    }
}