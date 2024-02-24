package com.fiap.restaurantes.service;

import com.fiap.restaurantes.exception.EntityNotFoundException;
import com.fiap.restaurantes.model.Restaurante;
import com.fiap.restaurantes.repository.RestauranteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RestauranteServiceImpl implements RestauranteService {

    private final RestauranteRepository repo;

    @Override
    public Restaurante salvar(Restaurante restaurante) {
        return repo.save(restaurante);
    }

    @Override
    public Restaurante buscar(Long id) {
        return repo.findById(id).orElseThrow(() -> new EntityNotFoundException(Restaurante.class.getSimpleName()));
    }

    @Override
    public Restaurante alterar(Restaurante restauranteNovo) {

        buscar(restauranteNovo.getId());

        return repo.save(restauranteNovo);
    }

    @Override
    public boolean remover(Long id) {
        return false;
    }

    @Override
    public List<Restaurante> listar() {
        return null;
    }
}
