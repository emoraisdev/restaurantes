package com.fiap.restaurantes.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String telefone;

    @Column(nullable = false)
    private String cpf;

    @OneToOne
    @JoinColumn(name = "endereco_id", referencedColumnName = "id")
    @Column(nullable = false)
    private Endereco endereco;
}
