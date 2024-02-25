package com.fiap.restaurantes.utils;

import com.fiap.restaurantes.model.Endereco;
import com.fiap.restaurantes.model.Restaurante;

public class RestauranteHelper {

    public static Restaurante gerarRestaurante() {
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
}
