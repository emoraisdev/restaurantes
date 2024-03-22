package com.fiap.restaurantes.service;

import com.fiap.restaurantes.entity.Avaliacao;
import com.fiap.restaurantes.repository.AvaliacaoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class AvaliacaoServiceTest {

    @Mock
    private AvaliacaoRepository avaliacaoRepository;

    @InjectMocks
    private AvaliacaoServiceImpl avaliacaoService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testSalvarAvaliacao() {
        Avaliacao avaliacao = new Avaliacao();
        avaliacao.setComentario("Ótimo restaurante!");
        avaliacao.setNota(5);

        when(avaliacaoRepository.save(avaliacao)).thenReturn(avaliacao);

        Avaliacao avaliacaoSalva = avaliacaoService.salvar(avaliacao);

        verify(avaliacaoRepository, times(1)).save(avaliacao);
        assertEquals(avaliacao, avaliacaoSalva);
    }

    @Test
    void testBuscarAvaliacaoPorId() {
        Avaliacao avaliacao = new Avaliacao();
        avaliacao.setId(1L);
        avaliacao.setComentario("Ótimo restaurante!");
        avaliacao.setNota(5);

        when(avaliacaoRepository.findById(1L)).thenReturn(Optional.of(avaliacao));

        Avaliacao avaliacaoEncontrada = avaliacaoService.buscar(1L);

        verify(avaliacaoRepository, times(1)).findById(1L);
        assertEquals(avaliacao, avaliacaoEncontrada);
    }

    @Test
    void testAlterarAvaliacao() {
        Avaliacao avaliacao = new Avaliacao();
        avaliacao.setId(1L);
        avaliacao.setComentario("Ótimo restaurante!");
        avaliacao.setNota(5);

        when(avaliacaoRepository.findById(1L)).thenReturn(Optional.of(avaliacao));
        when(avaliacaoRepository.save(avaliacao)).thenReturn(avaliacao);

        Avaliacao avaliacaoAlterada = avaliacaoService.alterar(avaliacao);

        verify(avaliacaoRepository, times(1)).findById(1L);
        verify(avaliacaoRepository, times(1)).save(avaliacao);
        assertEquals(avaliacao, avaliacaoAlterada);
    }

    @Test
    void testRemoverAvaliacao() {
        Avaliacao avaliacao = new Avaliacao();
        avaliacao.setId(1L);
        avaliacao.setComentario("Ótimo restaurante!");
        avaliacao.setNota(5);

        when(avaliacaoRepository.findById(1L)).thenReturn(Optional.of(avaliacao));

        boolean removido = avaliacaoService.remover(1L);

        verify(avaliacaoRepository, times(1)).findById(1L);
        verify(avaliacaoRepository, times(1)).deleteById(1L);
        assertTrue(removido);
    }

    @Test
    void testListarAvaliacoes() {
        Avaliacao avaliacao1 = new Avaliacao();
        avaliacao1.setId(1L);
        avaliacao1.setComentario("Comentário 1");
        avaliacao1.setNota(4);

        Avaliacao avaliacao2 = new Avaliacao();
        avaliacao2.setId(2L);
        avaliacao2.setComentario("Comentário 2");
        avaliacao2.setNota(5);

        List<Avaliacao> avaliacoes = Arrays.asList(avaliacao1, avaliacao2);

        Page<Avaliacao> page = new PageImpl<>(avaliacoes);

        when(avaliacaoRepository.findAll(any(Pageable.class))).thenReturn(page);

        Page<Avaliacao> avaliacoesListadas = avaliacaoService.listar(Pageable.unpaged());

        verify(avaliacaoRepository, times(1)).findAll(any(Pageable.class));

        assertEquals(avaliacoes.size(), avaliacoesListadas.getContent().size());
    }

}
