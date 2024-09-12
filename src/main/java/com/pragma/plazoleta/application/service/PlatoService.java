package com.pragma.plazoleta.application.service;

import com.pragma.plazoleta.domain.model.Plato;
import com.pragma.plazoleta.domain.model.Restaurante;
import com.pragma.plazoleta.domain.repository.PlatoRepository;
import com.pragma.plazoleta.domain.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;

@Service
public class PlatoService {

    @Autowired
    private PlatoRepository platoRepository;

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private RestTemplate restTemplate;

    private final String USUARIOS_SERVICE_URL = "http://usuarios:8080/{id}";

    public Plato crearPlato(@Valid Plato plato, Long restauranteId, Long propietarioId) {
        // Verificar que el restaurante existe
        Optional<Restaurante> restauranteOpt = restauranteRepository.findById(restauranteId);
        if (restauranteOpt.isEmpty()) {
            throw new IllegalArgumentException("Restaurante no encontrado.");
        }

        Restaurante restaurante = restauranteOpt.get();

        // Verificar que el usuario es propietario del restaurante
        UsuarioDTO propietario = restTemplate.getForObject(USUARIOS_SERVICE_URL, UsuarioDTO.class, propietarioId);

        if (propietario == null || !propietario.getRol().equals("PROPIETARIO") || !restaurante.getPropietarioId().equals(propietarioId)) {
            throw new IllegalArgumentException("El usuario no es propietario de este restaurante.");
        }

        // Asociar el restaurante al plato y guardar el plato
        plato.setRestaurante(restaurante);
        plato.setActivo(true);  // Activar el plato por defecto

        return platoRepository.save(plato);
    }

    public List<Plato> obtenerPlatosPorRestaurante(Long restauranteId) {
        return platoRepository.findByRestauranteId(restauranteId);
    }
}
