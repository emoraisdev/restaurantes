package com.fiap.restaurantes.model;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Avaliacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String comentario;

    @Column(nullable = false)
    private Integer nota;

    @ManyToOne
    @JoinColumn(name = "cliente_id", referencedColumnName = "id")
    @Column(nullable = false)
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "restaurante_id", referencedColumnName = "id")
    @Column(nullable = false)
    private Restaurante restaurante;
}
