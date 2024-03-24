package com.fiap.restaurantes.service;

import com.fiap.restaurantes.entity.Reserva;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ReservaService {
    Reserva criarReserva(Reserva reserva);

    Page<Reserva> listarReservas(Integer status, Pageable pageable);

    Optional<Reserva> alterarReserva(Long id, Reserva reserva);

    Optional<Reserva> obterReservaPorId(Long idReserva);
}

