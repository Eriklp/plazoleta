package com.pragma.plazoleta.web.controller;

import com.pragma.plazoleta.application.service.RestauranteService;
import com.pragma.plazoleta.domain.model.Restaurante;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.Optional;
import java.util.List;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

    private final RestauranteService restauranteService;

    public RestauranteController(RestauranteService restauranteService) {
        this.restauranteService = restauranteService;
    }

    @PostMapping
    public ResponseEntity<Restaurante> crearRestaurante(@Valid @RequestBody Restaurante restaurante,
                                                        @RequestParam Long propietarioId) {
        Restaurante nuevoRestaurante = restauranteService.crearRestaurante(restaurante, propietarioId);
        return ResponseEntity.ok(nuevoRestaurante);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Restaurante> obtenerRestaurantePorId(@PathVariable Long id) {
        Optional<Restaurante> restaurante = restauranteService.obtenerRestaurantePorId(id);
        return restaurante.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Restaurante>> listarRestaurantes() {
        return ResponseEntity.ok(restauranteService.listarRestaurantes());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarRestaurante(@PathVariable Long id) {
        restauranteService.eliminarRestaurante(id);
        return ResponseEntity.noContent().build();
    }
}
