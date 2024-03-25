package com.fiap.restaurantes.service;

import com.fiap.restaurantes.entity.Mesa;
import com.fiap.restaurantes.entity.enums.MesaStatus;
import com.fiap.restaurantes.exception.EntityNotFoundException;
import com.fiap.restaurantes.repository.MesaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MesaServiceImpl implements MesaService {

    private final MesaRepository mesaRepository;


    @Override
    public Mesa obterMesaPorId(Long id) {
        return mesaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Mesa.class.getSimpleName()));
    }

    @Override
    public Mesa criarMesa(Mesa cliente) {
        return mesaRepository.save(cliente);
    }

    @Override
    public Optional<Mesa> atualizarMesa(Mesa mesa) {
        if (mesaRepository.existsById(mesa.getId())) {
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
            mesas = mesaRepository.getMesasByStatus(MesaStatus.fromValue(status), pageable);
        }else{
            mesas = mesaRepository.findAll(pageable);
        }
        return mesas;
    }
}