package com.fiap.restaurantes.service;

import com.fiap.restaurantes.exception.EntityNotFoundException;
import com.fiap.restaurantes.entity.Restaurante;
import com.fiap.restaurantes.repository.RestauranteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RestauranteServiceImpl implements RestauranteService {

    private final RestauranteRepository repo;

    private final EnderecoService enderecoService;

    @Override
    @Transactional
    public Restaurante salvar(Restaurante restaurante) {
        var endereco = enderecoService.salvar(restaurante.getEndereco());

        restaurante.getEndereco().setId(endereco.getId());

        return repo.save(restaurante);
    }

    @Override
    public Restaurante buscar(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Restaurante.class.getSimpleName()));
    }

    @Override
    public Restaurante alterar(Restaurante restauranteNovo) {

        buscar(restauranteNovo.getId());

        enderecoService.salvar(restauranteNovo.getEndereco());

        return repo.save(restauranteNovo);
    }

    @Override
    public boolean remover(Long id) {
        buscar(id);
        repo.deleteById(id);

        return true;
    }

    @Override
    public Page<Restaurante> listar(Pageable pageable) {
        return repo.listarRestaurantes(pageable);
    }

}
