package com.fiap.restaurantes.service;

import com.fiap.restaurantes.entity.Mesa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface MesaService {

    Mesa obterMesaPorId(Long id);

    Mesa criarMesa(Mesa mesa);

    Optional<Mesa> atualizarMesa(Mesa mesa);

    boolean deletarMesaPorId(Long id);

    Page<Mesa> listarMesas(Integer status, Pageable pageable);
}

