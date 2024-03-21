package com.fiap.restaurantes.service;

import com.fiap.restaurantes.exception.EntityNotFoundException;
import com.fiap.restaurantes.entity.Avaliacao;
import com.fiap.restaurantes.repository.AvaliacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AvaliacaoServiceImpl implements AvaliacaoService {

    private final AvaliacaoRepository repo;

    @Override
    public Avaliacao salvar(Avaliacao avaliacao) {
        return repo.save(avaliacao);
    }

    @Override
    public Avaliacao buscar(Long id) {
        return repo.findById(id).orElseThrow(() -> new EntityNotFoundException(Avaliacao.class.getSimpleName()));
    }

    @Override
    public Avaliacao alterar(Avaliacao avaliacaoNova) {
        buscar(avaliacaoNova.getId());
        return repo.save(avaliacaoNova);
    }

    @Override
    public boolean remover(Long id) {
        buscar(id);
        repo.deleteById(id);
        return true;
    }

    @Override
    public Page<Avaliacao> listar(Pageable pageable) {
        return repo.findAll(pageable);
    }
}
