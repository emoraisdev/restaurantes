package com.fiap.restaurantes.service;

import com.fiap.restaurantes.entity.Restaurante;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RestauranteService {

    Restaurante salvar(Restaurante restaurante);

    Restaurante buscar(Long id);

    Restaurante alterar(Restaurante restaurante);

    boolean remover(Long id);

    Page<Restaurante> listar(Pageable pageable);

}
