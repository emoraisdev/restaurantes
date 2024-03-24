package com.fiap.restaurantes.repository;

import com.fiap.restaurantes.entity.Reserva;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    @Query("SELECT r FROM Reserva r ORDER BY r.id ASC")
    Page<Reserva> listarReservas(Pageable pageable);

    @Query("SELECT r FROM Reserva r where r.status = :status ORDER BY r.id ASC")
    Page<Reserva> listarReservasByStatus(int status, Pageable pageable);

    @Query("SELECT r FROM Reserva r WHERE r.restaurante.id = :restauranteId AND ELEMENT(r.mesa).id = :mesaId AND r.status = 1 AND (r.periodoDe < :periodoAte AND r.periodoAte > :periodoDe)")
    List<Reserva> findByRestauranteIdAndMesaIdPeriodoDeBetweenPeriodoAte(Long restauranteId, Long mesaId, LocalDateTime periodoDe, LocalDateTime periodoAte);
}
