package com.pragma.plazoleta.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.Set;

@Entity
public class Restaurante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 100)
    @Pattern(regexp = "^(?!\\d+$).*$", message = "El nombre del restaurante no puede contener solo números.")
    private String nombre;

    @NotBlank
    @Pattern(regexp = "\\d+", message = "El NIT debe ser numérico.")
    private String nit;

    @NotBlank
    private String direccion;

    @NotBlank
    @Size(max = 13)
    @Pattern(regexp = "^\\+?\\d+$", message = "El teléfono debe ser numérico y puede contener el símbolo '+'.")
    private String telefono;

    @NotBlank
    private String urlLogo;  // Añadido según los requerimientos

    private Long propietarioId;

    @OneToMany(mappedBy = "restaurante", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Plato> platos;

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getUrlLogo() {
        return urlLogo;
    }

    public void setUrlLogo(String urlLogo) {
        this.urlLogo = urlLogo;
    }

    public Long getPropietarioId() {
        return propietarioId;
    }

    public void setPropietarioId(Long propietarioId) {
        this.propietarioId = propietarioId;
    }

    public Set<Plato> getPlatos() {
        return platos;
    }

    public void setPlatos(Set<Plato> platos) {
        this.platos = platos;
    }
}
