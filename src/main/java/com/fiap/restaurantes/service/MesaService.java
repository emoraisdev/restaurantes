package com.fiap.restaurantes.service;

import com.fiap.restaurantes.model.Mesa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface MesaService {

    Optional<Mesa> obterMesaPorId(Long id);

    Mesa criarMesa(Mesa mesa);

    Optional<Mesa> atualizarMesa(Long id, Mesa mesa);

    boolean deletarMesaPorId(Long id);

    Page<Mesa> listarMesas(Integer status, Pageable pageable);
}

