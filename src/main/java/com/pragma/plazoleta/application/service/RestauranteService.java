package com.pragma.plazoleta.application.service;

import com.pragma.plazoleta.domain.model.Restaurante;
import com.pragma.plazoleta.domain.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;

@Service
@Validated
public class RestauranteService {

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private RestTemplate restTemplate;

    private final String USUARIOS_SERVICE_URL = "http://localhost:8081/api/usuarios/{id}";

    public Restaurante crearRestaurante(@Valid Restaurante restaurante, Long propietarioId) {
        // Llamar al microservicio de usuarios para validar el rol del usuario
        UsuarioDTO usuario = restTemplate.getForObject(USUARIOS_SERVICE_URL, UsuarioDTO.class, propietarioId);

        if (usuario == null || !usuario.getRol().equals("PROPIETARIO")) {
            throw new IllegalArgumentException("El usuario con ID " + propietarioId + " no es un propietario válido.");
        }

        // Asignar el propietario al restaurante
        restaurante.setPropietarioId(propietarioId);

        // Guardar el restaurante
        return restauranteRepository.save(restaurante);
    }

    public Optional<Restaurante> obtenerRestaurantePorId(Long id) {
        return restauranteRepository.findById(id);
    }

    public void eliminarRestaurante(Long id) {
        restauranteRepository.deleteById(id);
    }

    public List<Restaurante> listarRestaurantes() {
        return restauranteRepository.findAll();
    }
}
