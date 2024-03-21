package com.fiap.restaurantes.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime periodoDe;

    @Column(nullable = false)
    private LocalDateTime periodoAte;

    @Column(nullable = false)
    private int status;

    @ManyToOne
    @JoinColumn(name = "restaurante_id", referencedColumnName = "id", nullable = false)
    private Restaurante restaurante;

    @ManyToMany
    @JoinTable(
            name = "reserva_mesa",
            joinColumns = @JoinColumn(name = "mesa_id"),
            inverseJoinColumns = @JoinColumn(name = "id"))
    private List<Mesa> mesa;

    @ManyToOne
    @JoinColumn(name = "cliente_id", referencedColumnName = "id", nullable = false)
    private Cliente cliente;

}
