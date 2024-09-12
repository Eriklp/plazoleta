package com.pragma.plazoleta.web.controller;

import com.pragma.plazoleta.application.service.PlatoService;
import com.pragma.plazoleta.domain.model.Plato;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/platos")
public class PlatoController {

    private final PlatoService platoService;

    public PlatoController(PlatoService platoService) {
        this.platoService = platoService;
    }

    @PostMapping
    public ResponseEntity<Plato> crearPlato(@Valid @RequestBody Plato plato,
                                            @RequestParam Long restauranteId,
                                            @RequestParam Long propietarioId) {
        Plato nuevoPlato = platoService.crearPlato(plato, restauranteId, propietarioId);
        return ResponseEntity.ok(nuevoPlato);
    }

    @GetMapping("/restaurante/{restauranteId}")
    public ResponseEntity<List<Plato>> obtenerPlatosPorRestaurante(@PathVariable Long restauranteId) {
        List<Plato> platos = platoService.obtenerPlatosPorRestaurante(restauranteId);
        return ResponseEntity.ok(platos);
    }
}
