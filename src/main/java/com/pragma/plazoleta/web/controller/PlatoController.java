package com.pragma.plazoleta.web.controller;

import com.pragma.plazoleta.application.service.PlatoService;
import com.pragma.plazoleta.domain.model.Plato;
import com.pragma.plazoleta.application.dto.PlatoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/platos")
public class PlatoController {

    @Autowired
    private PlatoService platoService;

    @PreAuthorize("hasRole('PROPIETARIO') and #propietarioId == authentication.principal.id")
    @PostMapping("/crear")
    public ResponseEntity<Plato> crearPlato(
            @RequestParam Long propietarioId,
            @RequestParam Long restauranteId,
            @RequestBody PlatoDTO platoDTO) {

        Plato plato = platoService.crearPlato(new Plato(platoDTO), restauranteId, propietarioId);
        return new ResponseEntity<>(plato, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('PROPIETARIO') and #propietarioId == authentication.principal.id")
    @PutMapping("/{platoId}")
    public ResponseEntity<Plato> modificarPlato(
            @PathVariable Long platoId,
            @RequestParam Long propietarioId,
            @RequestBody PlatoDTO platoDTO) {

        Plato platoModificado = platoService.modificarPlato(platoId, propietarioId, platoDTO.getDescripcion(), platoDTO.getPrecio());
        return ResponseEntity.ok(platoModificado);
    }
}

