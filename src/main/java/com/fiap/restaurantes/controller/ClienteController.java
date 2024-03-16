package com.fiap.restaurantes.controller;

import com.fiap.restaurantes.model.Cliente;
import com.fiap.restaurantes.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;

    @GetMapping
    public ResponseEntity<List<Cliente>> obterTodosClientes() {
        List<Cliente> clientes = clienteService.obterTodosClientes();
        return ResponseEntity.ok(clientes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> obterClientePorId(@PathVariable Long id) {
        Optional<Cliente> cliente = clienteService.obterClientePorId(id);
        return cliente.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Cliente> criarCliente(@RequestBody Cliente cliente) {
        Cliente novoCliente = clienteService.criarCliente(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoCliente);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cliente> atualizarCliente(@PathVariable Long id, @RequestBody Cliente cliente) {
        Optional<Cliente> clienteAtualizado = clienteService.atualizarCliente(id, cliente);
        return clienteAtualizado.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarCliente(@PathVariable Long id) {
        boolean deletado = clienteService.deletarCliente(id);
        return deletado ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @GetMapping("/listar")
    public ResponseEntity<Page<Cliente>> listarClientes(Pageable pageable) {
        Page<Cliente> clientes = clienteService.listar(pageable);
        return ResponseEntity.ok(clientes);
    }
}
