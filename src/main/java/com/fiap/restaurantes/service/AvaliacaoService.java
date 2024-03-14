package com.fiap.restaurantes.service;

import com.fiap.restaurantes.model.Avaliacao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AvaliacaoService {

    Avaliacao salvar(Avaliacao avaliacao);

    Avaliacao buscar(Long id);

    Avaliacao alterar(Avaliacao avaliacao);

    boolean remover(Long id);

    Page<Avaliacao> listar(Pageable pageable);
}
