package com.fiap.restaurantes.service;

import com.fiap.restaurantes.entity.Endereco;
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
        return null;
    }

    @Override
    public Endereco alterar(Endereco endereco) {
        return null;
    }

    @Override
    public boolean remover(Long id) {
        return false;
    }

}
