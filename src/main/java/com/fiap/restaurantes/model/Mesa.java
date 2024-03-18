package com.fiap.restaurantes.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Mesa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer numero;

    @Column(nullable = false)
    private int status;

    @ManyToOne
    @JoinColumn(name = "restaurante_id", referencedColumnName = "id", nullable = false)
    private Restaurante restaurante;
}
