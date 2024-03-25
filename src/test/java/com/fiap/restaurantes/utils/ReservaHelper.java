package com.fiap.restaurantes.utils;

import com.fiap.restaurantes.entity.Reserva;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ReservaHelper {
    public static List<Reserva> getListaReservas() {
        List<Reserva> reservas = new ArrayList<>();
        reservas.add(getReserva(1L, LocalDateTime.of(2024, 3, 25, 17, 0, 0), LocalDateTime.of(2024, 3, 25, 18, 0, 0), 4));
        reservas.add(getReserva(2L, LocalDateTime.of(2024, 3, 26, 17, 0, 0), LocalDateTime.of(2024, 3, 26, 18, 0, 0), 4));
        return reservas;
    }

    public static Reserva getReserva(Long id, LocalDateTime periodoDe, LocalDateTime periodoAte, int qtdPessoasReserva) {
        return Reserva.builder()
                .id(id)
                .periodoDe(periodoDe)
                .periodoAte(periodoAte)
                .restaurante(RestauranteHelper.gerarRestaurante())
                .mesa(MesaHelper.gerarListaMesa())
                .cliente(ClienteHelper.gerarCliente())
                .qtdPessoasReserva(qtdPessoasReserva)
                .build();
    }
}
