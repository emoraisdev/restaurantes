package com.fiap.restaurantes.service;

import com.fiap.restaurantes.model.Restaurante;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RestauranteService {

    Restaurante salvar(Restaurante restaurante);

    Restaurante buscar(Long id);

    Restaurante alterar(Long id, Restaurante restaurante);

    boolean remover(Long id);

    List<Restaurante> listar();
}
