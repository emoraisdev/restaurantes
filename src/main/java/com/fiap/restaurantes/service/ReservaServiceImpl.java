package com.fiap.restaurantes.service;

import com.fiap.restaurantes.entity.Cliente;
import com.fiap.restaurantes.entity.Reserva;
import com.fiap.restaurantes.exception.BusinessException;
import com.fiap.restaurantes.exception.EntityNotFoundException;
import com.fiap.restaurantes.repository.ReservaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class ReservaServiceImpl implements ReservaService {

    private final ReservaRepository reservaRepository;

    private final RestauranteService restauranteService;

    private final ClienteService clienteService;

    private final MesaService mesaService;

    @Override
    public Reserva criarReserva(Reserva reserva) {

        verificarRestaurante(reserva);
        verificarCliente(reserva);
        verificarMesa(reserva);

        if(reserva.getPeriodoDe().isAfter(reserva.getPeriodoAte())){
            throw new BusinessException("peridoDe é maior que o periodoAte");
        }

        reserva.getMesa().forEach(mesa -> {
            List<Reserva> reservaList =  reservaRepository.findByRestauranteIdAndMesaIdPeriodoDeBetweenPeriodoAte(reserva.getRestaurante().getId(), mesa.getId(), reserva.getPeriodoDe(), reserva.getPeriodoAte());
            if(!reservaList.isEmpty()){
                throw new BusinessException("Já existe reserva para a mesa " + mesa.getId() + " no horario escolhido");
            }
        });


        clienteService.obterClientePorId(reserva.getCliente().getId()).ifPresent(reserva::setCliente);
        reserva.setRestaurante(restauranteService.buscar(reserva.getRestaurante().getId()));

        return reservaRepository.save(reserva);
    }

    private void verificarRestaurante(Reserva reserva) {
        restauranteService.buscar(reserva.getRestaurante().getId());
    }

    private Cliente verificarCliente(Reserva reserva) {
        return clienteService.obterClientePorId(reserva.getCliente().getId()).orElseThrow(() -> new EntityNotFoundException(Cliente.class.getSimpleName()));
    }

    private void verificarMesa(Reserva reserva) {
        reserva.getMesa().forEach(m->
                mesaService.obterMesaPorId(m.getId())
        );

    }

    @Override
    public Page<Reserva> listarReservas(Pageable pageable) {
        return reservaRepository.listarReservas(pageable);
    }

    @Override
    public Optional<Reserva> alterarReserva(Long id, Reserva reserva) {
        if (reservaRepository.existsById(id)) {
            reserva.setId(id);
            return Optional.of(reservaRepository.save(reserva));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Reserva> obterReservaPorId(Long id) {
        return Optional.ofNullable(reservaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Reserva.class.getSimpleName())));
    }
}
