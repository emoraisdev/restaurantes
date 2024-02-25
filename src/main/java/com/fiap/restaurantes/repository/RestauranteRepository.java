package com.fiap.restaurantes.repository;

import com.fiap.restaurantes.model.Restaurante;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RestauranteRepository extends JpaRepository<Restaurante, Long> {
    @Query("SELECT r FROM Restaurante r ORDER BY r.nome ASC")
    Page<Restaurante> listarRestaurantes(Pageable pageable);
}
