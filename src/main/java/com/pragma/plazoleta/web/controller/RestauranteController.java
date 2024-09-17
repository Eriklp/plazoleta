// com.pragma.plazoleta.web.controller.RestauranteController.java
package com.pragma.plazoleta.web.controller;

import com.pragma.plazoleta.application.service.RestauranteService;
import com.pragma.plazoleta.domain.model.Restaurante;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

    @Autowired
    private RestauranteService restauranteService;

    @PostMapping("/crear")
    public ResponseEntity<Restaurante> crearRestaurante(@RequestBody Restaurante restaurante, @RequestParam Long idUsuario) {
        Restaurante nuevoRestaurante = restauranteService.crearRestaurante(restaurante, idUsuario);
        return new ResponseEntity<>(nuevoRestaurante, HttpStatus.CREATED);
    }

    @GetMapping("/usuario/{id}")
    public ResponseEntity<?> obtenerUsuario(@PathVariable Long id) {
        return ResponseEntity.ok(restauranteService.obtenerUsuarioPorId(id));
    }
}
