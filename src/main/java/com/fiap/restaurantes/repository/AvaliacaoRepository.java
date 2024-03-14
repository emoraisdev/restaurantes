package com.fiap.restaurantes.repository;

import com.fiap.restaurantes.model.Avaliacao;
import com.fiap.restaurantes.model.Restaurante;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Long> {

     Page<Avaliacao> findByRestaurante(Restaurante restaurante, Pageable pageable);
}
