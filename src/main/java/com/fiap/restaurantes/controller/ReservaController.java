package com.fiap.restaurantes.controller;

import com.fiap.restaurantes.entity.Reserva;
import com.fiap.restaurantes.service.ReservaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/reserva")
@RequiredArgsConstructor
public class ReservaController {

    private final ReservaService reservaService;

    @PostMapping
    public ResponseEntity<Reserva> criarReserva(@RequestBody Reserva reserva) {
        Reserva novaReserva = reservaService.criarReserva(reserva);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaReserva);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reserva> obterReservaPorId(@PathVariable Long id) {
        Optional<Reserva> reserva = reservaService.obterReservaPorId(id);
        return reserva.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/listar")
    public ResponseEntity<Page<Reserva>> listarReservas(@RequestParam(required = false) Integer status, Pageable pageable) {
        Page<Reserva> mesas = reservaService.listarReservas(status, pageable);
        return ResponseEntity.ok(mesas);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Reserva> atualizarReserva(@PathVariable Long id, @RequestBody Reserva reserva) {
        Optional<Reserva> reservaAtualizada = reservaService.alterarReserva(id, reserva);
        return reservaAtualizada.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
