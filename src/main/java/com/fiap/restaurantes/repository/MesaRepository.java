package com.fiap.restaurantes.repository;

import com.fiap.restaurantes.entity.Mesa;
import com.fiap.restaurantes.entity.enums.MesaStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MesaRepository extends JpaRepository<Mesa, Long> {
    Page<Mesa> getMesasByStatus(MesaStatus status, Pageable pageable);
}