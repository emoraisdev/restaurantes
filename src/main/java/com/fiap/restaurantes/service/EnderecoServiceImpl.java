package com.fiap.restaurantes.service;

import com.fiap.restaurantes.entity.Endereco;
import com.fiap.restaurantes.exception.EntityNotFoundException;
import com.fiap.restaurantes.exception.ParameterNotFoundException;
import com.fiap.restaurantes.repository.EnderecoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EnderecoServiceImpl implements EnderecoService{

    private final EnderecoRepository repo;

    @Override
    public Endereco salvar(Endereco endereco) {
        return repo.save(endereco);
    }

    @Override
    public Endereco buscar(Long id) {
        return repo.findById(id).orElseThrow(() -> new EntityNotFoundException(Endereco.class.getSimpleName()));
    }

    @Override
    public Endereco alterar(Endereco endereco) {

        if (endereco == null || endereco.getId() == null) {
            throw new ParameterNotFoundException("id Endereço");
        }

        buscar(endereco.getId());
        return repo.save(endereco);
    }

    @Override
    public boolean remover(Long id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

}
