package com.fiap.restaurantes.entity;

import com.fiap.restaurantes.entity.enums.MesaStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Mesa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer numero;

    @Column(nullable = false)
    private MesaStatus status;

    @ManyToOne
    @JoinColumn(name = "restaurante_id", referencedColumnName = "id", nullable = false)
    private Restaurante restaurante;
}
