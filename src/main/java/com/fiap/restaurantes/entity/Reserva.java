package com.fiap.restaurantes.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime periodoDe;

    @Column(nullable = false)
    private LocalDateTime periodoAte;

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

    @Column(nullable = false)
    @Min(value=1, message="Quantidade de pessoas invalidas para a reserva")
    @Max(value=4, message="Quantidade de pessoas por reserva excedida, limite de até 4 pessoas")
    private int qtdPessoasReserva;

}
