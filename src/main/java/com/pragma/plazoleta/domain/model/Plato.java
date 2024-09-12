package com.pragma.plazoleta.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Entity
public class Plato {

    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    @NotBlank
    private String nombre;

    @Getter
    @Setter
    @Positive(message = "El precio debe ser un número entero positivo mayor que 0")
    private Integer precio;

    @Getter
    @Setter
    @NotBlank
    private String descripcion;

    @Getter
    @Setter
    @NotBlank
    private String urlImagen;

    @Getter
    @Setter
    @NotBlank
    private String categoria;

    @Getter
    @Setter
    private boolean activo = true;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "restaurante_id", nullable = false)
    private Restaurante restaurante;

}
