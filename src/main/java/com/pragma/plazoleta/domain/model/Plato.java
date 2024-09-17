package com.pragma.plazoleta.domain.model;

import com.pragma.plazoleta.application.dto.PlatoDTO;
import jakarta.persistence.*;
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
    private String nombre;

    @Getter
    @Setter
    private Integer precio;

    @Getter
    @Setter
    private String descripcion;

    @Getter
    @Setter
    private String urlImagen;

    @Getter
    @Setter
    private String categoria;

    @Getter
    @Setter
    private boolean activo;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "restaurante_id", nullable = false)
    private Restaurante restaurante;

    // Constructor para aceptar un PlatoDTO
    public Plato(PlatoDTO platoDTO) {
        this.nombre = platoDTO.getNombre();
        this.precio = platoDTO.getPrecio();
        this.descripcion = platoDTO.getDescripcion();
        this.urlImagen = platoDTO.getUrlImagen();
        this.categoria = platoDTO.getCategoria();
    }

    // Constructor vacío por defecto (necesario para JPA y los tests)
    public Plato() {
    }
}
