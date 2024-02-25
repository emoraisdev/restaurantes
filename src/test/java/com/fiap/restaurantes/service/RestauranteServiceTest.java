package com.fiap.restaurantes.service;

import com.fiap.restaurantes.exception.EntityNotFoundException;
import com.fiap.restaurantes.model.Restaurante;
import com.fiap.restaurantes.repository.RestauranteRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.Optional;

import static com.fiap.restaurantes.utils.RestauranteHelper.gerarRestaurante;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class RestauranteServiceTest {

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

        var id = 1001L;
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
    void deveGerarExcecao_QuandoAlterarRestaurante_IdNaoExiste() {

        var id = 1002L;
        var restaurante = gerarRestaurante();
        restaurante.setId(id);

        when(repo.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.alterar(restaurante))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Entidade Restaurante não encontrada.");

        verify(repo, times(1)).findById(any(Long.class));
        verify(repo, never()).save(any(Restaurante.class));
    }

    @Test
    void devePermitirBuscarRestaurante() {
        var id = 1003L;
        var restaurante = gerarRestaurante();
        restaurante.setId(id);

        when(repo.findById(id)).thenReturn(Optional.of(restaurante));

        var restauranteRetornado = service.buscar(id);

        assertThat(restauranteRetornado).isEqualTo(restaurante);
        verify(repo, times(1)).findById(any(Long.class));
    }

    @Test
    void deveGerarExcecao_QuandoBuscarRestaurante_IdNaoExiste() {

        var id = 1004L;
        var restaurante = gerarRestaurante();
        restaurante.setId(id);

        when(repo.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.buscar(id))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Entidade Restaurante não encontrada.");

        verify(repo, times(1)).findById(any(Long.class));
    }

    @Test
    void devePermitirRemoverRestaurante() {

        var id = 1004L;
        var restaurante = gerarRestaurante();
        restaurante.setId(id);

        when(repo.findById(id)).thenReturn(Optional.of(restaurante));
        doNothing().when(repo).deleteById(id);

        var isRestauranteRemovido = service.remover(id);

        assertThat(isRestauranteRemovido).isTrue();

        verify(repo, times(1)).findById(any(Long.class));
        verify(repo, times(1)).deleteById(any(Long.class));
    }

    @Test
    void deveGerarExcecao_QuandoRemoverRestaurante_IdNaoExiste() {

        var id = 1005L;

        when(repo.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.remover(id))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Entidade Restaurante não encontrada.");

        verify(repo, times(1)).findById(any(Long.class));
        verify(repo, never()).deleteById(any(Long.class));
    }

    @Test
    void devePermitirListarRestaurante() {

        //Arrange
        Page<Restaurante> page = new PageImpl<>(
                Arrays.asList(gerarRestaurante(), gerarRestaurante(), gerarRestaurante()));

        when(repo.listarRestaurantes(any(Pageable.class))).thenReturn(page);

        //Act
        var resultadoObtido = service.listar(Pageable.unpaged());

        //Assert
        assertThat(resultadoObtido).hasSize(3);
        assertThat(resultadoObtido.getContent()).asList()
                .allSatisfy(restaurante -> {
                    assertThat(restaurante).isNotNull().isInstanceOf(Restaurante.class);
                });

        verify(repo, times(1)).listarRestaurantes(any(Pageable.class));
    }

    private void assertRestaurante(Restaurante restauranteBD, Restaurante restauranteNovo) {
        assertThat(restauranteBD).isInstanceOf(Restaurante.class).isNotNull();
        assertThat(restauranteBD.getNome()).isEqualTo(restauranteNovo.getNome());
        assertThat(restauranteBD.getCapacidade()).isEqualTo(restauranteNovo.getCapacidade());
        assertThat(restauranteBD.getTipoCozinha()).isEqualTo(restauranteNovo.getTipoCozinha());
        assertThat(restauranteBD.getTelefone()).isEqualTo(restauranteNovo.getTelefone());
        assertThat(restauranteBD.getHorarioFuncionamento()).isEqualTo(restauranteNovo.getHorarioFuncionamento());
        assertThat(restauranteBD.getEndereco()).isNotNull();
    }
}
