package com.fiap.restaurantes.controller;

import com.fiap.restaurantes.model.Avaliacao;
import com.fiap.restaurantes.service.AvaliacaoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/avaliacoes")
public class AvaliacaoController {

    private final AvaliacaoService avaliacaoService;

    public AvaliacaoController(AvaliacaoService avaliacaoService) {
        this.avaliacaoService = avaliacaoService;
    }

    @PostMapping
    public ResponseEntity<Avaliacao> criarAvaliacao(@RequestBody Avaliacao avaliacao) {
        Avaliacao novaAvaliacao = avaliacaoService.salvar(avaliacao);
        return new ResponseEntity<>(novaAvaliacao, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Avaliacao> obterAvaliacao(@PathVariable Long id) {
        Avaliacao avaliacao = avaliacaoService.buscar(id);
        return ResponseEntity.ok(avaliacao);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Avaliacao> atualizarAvaliacao(@PathVariable Long id, @RequestBody Avaliacao avaliacao) {
        avaliacao.setId(id);
        Avaliacao avaliacaoAtualizada = avaliacaoService.alterar(avaliacao);
        return ResponseEntity.ok(avaliacaoAtualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirAvaliacao(@PathVariable Long id) {
        avaliacaoService.remover(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Page<Avaliacao>> listarAvaliacoes(Pageable pageable) {
        Page<Avaliacao> avaliacoes = avaliacaoService.listar(pageable);
        return ResponseEntity.ok(avaliacoes);
    }
}
