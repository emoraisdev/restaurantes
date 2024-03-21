package com.fiap.restaurantes.service;

import com.fiap.restaurantes.entity.Endereco;

public interface EnderecoService {

    Endereco salvar(Endereco endereco);

    Endereco buscar(Long id);

    Endereco alterar(Endereco endereco);

    boolean remover(Long id);
}
