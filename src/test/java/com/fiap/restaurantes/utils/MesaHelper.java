package com.fiap.restaurantes.utils;

import com.fiap.restaurantes.entity.Endereco;
import com.fiap.restaurantes.entity.Mesa;
import com.fiap.restaurantes.entity.Restaurante;

import java.util.ArrayList;
import java.util.List;

public class MesaHelper {

    public static List<Mesa> gerarListaMesa() {
        List<Mesa> listaMesa =  new ArrayList<>();
        listaMesa.add(gerarMesa(1L, 1));
        listaMesa.add(gerarMesa(2L,2));
        return listaMesa;
    }

    public static Mesa gerarMesa(Long id, int numero){
       return Mesa.builder()
                .id(id)
                .status(1)
                .numero(numero)
                .restaurante(RestauranteHelper.gerarRestaurante())
                .build();
    }
}
