package com.fiap.restaurantes.service;

import com.fiap.restaurantes.model.Cliente;
import com.fiap.restaurantes.model.Mesa;
import com.fiap.restaurantes.repository.MesaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MesaServiceImpl implements MesaService {

    private final MesaRepository mesaRepository;


    @Override
    public Optional<Mesa> obterMesaPorId(Long id) {
        return mesaRepository.findById(id);
    }

    @Override
    public Mesa criarMesa(Mesa cliente) {
        return mesaRepository.save(cliente);
    }

    @Override
    public Optional<Mesa> atualizarMesa(Long id, Mesa mesa) {
        if (mesaRepository.existsById(id)) {
            mesa.setId(id);
            return Optional.of(mesaRepository.save(mesa));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public boolean deletarMesaPorId(Long id) {
        if (mesaRepository.existsById(id)) {
            mesaRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Page<Mesa> listarMesas(Integer status, Pageable pageable) {
        Page<Mesa> mesas = null;

        if(status != null){
            mesas = mesaRepository.getMesasByStatus(status.intValue(), pageable);
        }else{
            mesas = mesaRepository.obterTodasMesas(pageable);
        }
        return mesas;
    }
}