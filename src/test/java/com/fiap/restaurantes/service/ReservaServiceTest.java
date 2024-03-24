package com.fiap.restaurantes.service;

import com.fiap.restaurantes.entity.Cliente;
import com.fiap.restaurantes.entity.Mesa;
import com.fiap.restaurantes.entity.Reserva;
import com.fiap.restaurantes.entity.Restaurante;
import com.fiap.restaurantes.exception.BusinessException;
import com.fiap.restaurantes.repository.ClienteRepository;
import com.fiap.restaurantes.repository.MesaRepository;
import com.fiap.restaurantes.repository.ReservaRepository;
import com.fiap.restaurantes.repository.RestauranteRepository;
import com.fiap.restaurantes.utils.ClienteHelper;
import com.fiap.restaurantes.utils.MesaHelper;
import com.fiap.restaurantes.utils.ReservaHelper;
import com.fiap.restaurantes.utils.RestauranteHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReservaServiceTest {
    @Mock
    private ReservaRepository reservaRepository;

    @InjectMocks
    private ReservaServiceImpl reservaService;

    @InjectMocks
    private ClienteServiceImpl clienteService;

    @InjectMocks
    private RestauranteServiceImpl restauranteService;

    @Mock
    private RestauranteRepository restauranteRepository;

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private MesaRepository mesaRepository;
    @InjectMocks
    private MesaServiceImpl mesaService;

    private EnderecoService enderecoService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        restauranteService = new RestauranteServiceImpl(restauranteRepository,enderecoService);
        reservaService = new ReservaServiceImpl(reservaRepository, restauranteService, clienteService,mesaService);
    }

    @Test
    void deveObterTodasReservas() {
        List<Reserva> reservas =  ReservaHelper.getListaReservas();

        when(reservaRepository.listarReservas(Pageable.unpaged())).thenReturn(new PageImpl<>(reservas));

        Page<Reserva> resultado = reservaService.listarReservas(null, Pageable.unpaged());

        assertEquals(reservas.size(), resultado.getContent().size());
    }

    @Test
    void deveObterReservasPorStatus() {
        List<Reserva> reservas =  ReservaHelper.getListaReservas();

        when(reservaRepository.listarReservasByStatus(1,Pageable.unpaged())).thenReturn(new PageImpl<>(reservas));

        Page<Reserva> resultado = reservaService.listarReservas(1, Pageable.unpaged());

        assertEquals(reservas.size(), resultado.getContent().size());
    }

    @Test
    void deveObterMesaPorId() {
        Reserva reserva = getReserva();

        when(reservaRepository.findById(1L)).thenReturn(Optional.of(reserva));

        Optional<Reserva> resultado = reservaService.obterReservaPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals(reserva, resultado.get());
    }

    @Test
    void devePermitirCriarReserva() {
        Reserva reserva = getReserva();

        Restaurante restaurante = RestauranteHelper.gerarRestaurante();
        restaurante.setId(1L);
        Cliente cliente = ClienteHelper.gerarCliente();
        Mesa mesa = MesaHelper.gerarMesa(1L, 1);
        mesa.setRestaurante(restaurante);
        List<Mesa> listMesa = new ArrayList<>();
        listMesa.add(mesa);

        reserva.setMesa(listMesa);

        when(restauranteRepository.findById(any())).thenReturn(Optional.of(restaurante));
        when(mesaRepository.findById(any())).thenReturn(Optional.of(mesa));
        when(clienteService.obterClientePorId(any())).thenReturn(Optional.of(cliente));
        when(reservaRepository.save(any())).thenReturn(reserva);

        Reserva novaReserva = reservaService.criarReserva(reserva);

        assertEquals(reserva, novaReserva);
    }

    @Test
    void devePermitirAtualizarReserva() {
        Reserva reserva = getReserva();

        when(reservaRepository.existsById(1L)).thenReturn(true);
        when(reservaRepository.save(reserva)).thenReturn(reserva);

        Optional<Reserva> reservaUpdate = reservaService.alterarReserva(reserva.getId(), reserva);

        verify(reservaRepository, times(1)).existsById(1L);
        verify(reservaRepository, times(1)).save(reserva);
        assertTrue(reservaUpdate.isPresent());
        assertEquals(reserva, reservaUpdate.get());
    }

    @Test
    void NaoDevePermitirAtualizarReserva_QuandoNaoExistirIdReserva() {
        Reserva reserva = getReserva();

        when(reservaRepository.existsById(3L)).thenReturn(false);

        Optional<Reserva> reservaUpdate = reservaService.alterarReserva(3L, null);

        verify(reservaRepository, never()).save(any());
        assertTrue(reservaUpdate.isEmpty());
    }



    private Reserva getReserva(){
      return ReservaHelper.getReserva(1L, 1, LocalDateTime.of(2024, 3, 22, 17, 0, 0), LocalDateTime.of(2024, 3, 22, 18, 0, 0),4);
    }

}
