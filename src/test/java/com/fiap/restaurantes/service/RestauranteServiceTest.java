package com.fiap.restaurantes.service;

import com.fiap.restaurantes.exception.EntityNotFoundException;
import com.fiap.restaurantes.model.Endereco;
import com.fiap.restaurantes.model.Restaurante;
import com.fiap.restaurantes.repository.RestauranteRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.swing.text.html.Option;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class RestauranteServiceTest {

    @Mock
    private RestauranteRepository repo;

    private RestauranteService service;

    AutoCloseable mock;

    @BeforeEach
    void setup() {
        mock = MockitoAnnotations.openMocks(this);
        service = new RestauranteServiceImpl(repo);
    }

    @AfterEach
    void tearDown() throws Exception {
        mock.close();
    }

    @Test
    void devePermitirSalvarRestaurante() {

        var restaurante = gerarRestaurante();

        when(repo.save(any(Restaurante.class))).thenAnswer(i -> i.getArgument(0));

        var restauranteRegistrado = service.salvar(restaurante);

        assertRestaurante(restauranteRegistrado, restaurante);

        verify(repo, timeout(1)).save(any(Restaurante.class));
    }

    @Test
    void devePermitirAlterarRestaurante() {

        var id = ThreadLocalRandom.current().nextLong();
        var restaurante = gerarRestaurante();
        restaurante.setId(id);

        var restauranteNovo = gerarRestaurante();
        restauranteNovo.setId(restaurante.getId());
        restauranteNovo.setTipoCozinha("Pizza e Massas");
        restauranteNovo.setNome("Pizza Top");
        restauranteNovo.setCapacidade(100);
        restauranteNovo.setHorarioFuncionamento("24h por dia");
        restauranteNovo.setTelefone("41 22222-2222");

        when(repo.findById(id)).thenReturn(Optional.of(restaurante));
        when(repo.save(restauranteNovo)).thenAnswer(i -> i.getArgument(0));

        var restauranteAlterado = service.alterar(restauranteNovo);

        assertRestaurante(restauranteAlterado, restauranteNovo);

        verify(repo, times(1)).findById(any(Long.class));
        verify(repo, times(1)).save(any(Restaurante.class));
    }

    @Test
    void deveGerarExcecao_QuandoAlterarRestaurante_IdNaoExiste(){

        var id = ThreadLocalRandom.current().nextLong();
        var restaurante = gerarRestaurante();
        restaurante.setId(id);

        when(repo.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() ->  service.alterar(restaurante))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Entidade Restaurante não encontrada.");

        verify(repo, times(1)).findById(any(Long.class));
        verify(repo, never()).save(any(Restaurante.class));
    }

    @Test
    void devePermitirBuscarRestaurante() {
        var id = ThreadLocalRandom.current().nextLong();
        var restaurante = gerarRestaurante();
        restaurante.setId(id);

        when(repo.findById(id)).thenReturn(Optional.of(restaurante));

        var restauranteRetornado = service.buscar(id);

        assertThat(restauranteRetornado).isEqualTo(restaurante);
        verify(repo, times(1)).findById(any(Long.class));
    }

    @Test
    void deveGerarExcecao_QuandoBuscarRestaurante_IdNaoExiste(){

        var id = ThreadLocalRandom.current().nextLong();
        var restaurante = gerarRestaurante();
        restaurante.setId(id);

        when(repo.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() ->  service.buscar(id))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Entidade Restaurante não encontrada.");

        verify(repo, times(1)).findById(any(Long.class));
    }

    @Test
    void devePermitirRemoverRestaurante() {

    }

    @Test
    void devePermitirListarRestaurante() {

    }

    private Restaurante gerarRestaurante() {
        return Restaurante.builder()
                .nome("Cantina da Vó")
                .capacidade(50)
                .telefone("11 11111-1111")
                .tipoCozinha("Caseira")
                .horarioFuncionamento("16:00h as 23:30h todos os dias")
                .endereco(Endereco.builder().rua("Rua XV")
                        .bairro("Cruzeiro")
                        .numero("121")
                        .cep("12345-321")
                        .cidade("Curitiba")
                        .estado("PR")
                        .pais("Brasil").build()).build();
    }

    private void assertRestaurante(Restaurante restauranteBD, Restaurante restauranteNovo){
        assertThat(restauranteBD).isInstanceOf(Restaurante.class).isNotNull();
        assertThat(restauranteBD.getNome()).isEqualTo(restauranteNovo.getNome());
        assertThat(restauranteBD.getCapacidade()).isEqualTo(restauranteNovo.getCapacidade());
        assertThat(restauranteBD.getTipoCozinha()).isEqualTo(restauranteNovo.getTipoCozinha());
        assertThat(restauranteBD.getTelefone()).isEqualTo(restauranteNovo.getTelefone());
        assertThat(restauranteBD.getHorarioFuncionamento()).isEqualTo(restauranteNovo.getHorarioFuncionamento());
        assertThat(restauranteBD.getEndereco()).isNotNull();
    }
}
