package com.fiap.restaurantes.controller;

import com.fiap.restaurantes.entity.Mesa;
import com.fiap.restaurantes.exception.EntityNotFoundException;
import com.fiap.restaurantes.service.MesaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/mesa")
@RequiredArgsConstructor
public class MesaController {

    private final MesaService mesaService;

    @GetMapping("/{id}")
    public ResponseEntity<Object> obterMesaPorId(@PathVariable Long id) {
        try {
            Mesa mesa = mesaService.obterMesaPorId(id);
            return new ResponseEntity<>(mesa, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping
    public ResponseEntity<Mesa> criarMesa(@RequestBody Mesa cliente) {
        Mesa mesa = mesaService.criarMesa(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(mesa);
    }

    @PutMapping
    public ResponseEntity<Mesa> atualizarMesa(@RequestBody Mesa mesa) {
        Optional<Mesa> mesaAtualizada = mesaService.atualizarMesa(mesa);
        return mesaAtualizada.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarMesa(@PathVariable Long id) {
        boolean mesaDeletada = mesaService.deletarMesaPorId(id);
        return mesaDeletada ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @GetMapping("/listar")
    public ResponseEntity<Page<Mesa>> listarMesas(@RequestParam(required = false) Integer status, Pageable pageable) {
        Page<Mesa> mesas = mesaService.listarMesas(status, pageable);
        return ResponseEntity.ok(mesas);
    }
}
