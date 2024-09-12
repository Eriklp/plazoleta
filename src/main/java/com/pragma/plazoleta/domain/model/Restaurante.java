package com.pragma.plazoleta.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
public class Restaurante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    private String nombre;

    @Getter
    @Setter
    private String direccion;

    @Getter
    @Setter
    @OneToMany(mappedBy = "restaurante", cascade = CascadeType.ALL)
    private Set<Plato> platos;

    public void setPropietarioId(Long propietarioId) {
    }

}
