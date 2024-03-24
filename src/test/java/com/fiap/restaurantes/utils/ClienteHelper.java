package com.fiap.restaurantes.utils;

import com.fiap.restaurantes.entity.Cliente;
import com.fiap.restaurantes.entity.Endereco;

public class ClienteHelper {

    public static Cliente gerarCliente() {
        return Cliente.builder()
                .id(1L)
                .nome("João da Silva")
                .endereco(Endereco.builder().rua("Rua Isabel")
                        .bairro("Paraiso")
                        .numero("19")
                        .cep("12345-321")
                        .cidade("São Paulo")
                        .estado("SP")
                        .pais("Brasil").build())
                .cpf("12345678900")
                .email("teste@teste.com")
                .telefone("11 98765-4321")
                .build();
    }
}
