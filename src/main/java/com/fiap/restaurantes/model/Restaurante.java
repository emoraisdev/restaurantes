package com.fiap.restaurantes.model;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Restaurante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String telefone;

    @OneToOne
    @JoinColumn(name = "endereco_id", referencedColumnName = "id")
    @Column(nullable = false)
    private Endereco endereco;

    @Column(nullable = false)
    private String tipoCozinha;

    @Column(nullable = false)
    private String horarioFuncionamento;

    @Column(nullable = false)
    private Integer capacidade;
}
