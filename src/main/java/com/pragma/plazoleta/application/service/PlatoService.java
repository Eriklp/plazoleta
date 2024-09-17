package com.pragma.plazoleta.application.service;

import com.pragma.plazoleta.application.dto.UsuarioDTO;
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

    /**
     * Crear un nuevo plato asociado a un restaurante.
     */
    public Plato crearPlato(@Valid Plato plato, Long restauranteId, Long propietarioId) {
        Restaurante restaurante = verificarRestaurante(restauranteId);
        verificarPropietario(restaurante, propietarioId);

        // Asociar el restaurante al plato y activar por defecto
        plato.setRestaurante(restaurante);
        plato.setActivo(true);

        return platoRepository.save(plato);
    }

    /**
     * Obtener los platos de un restaurante por su ID.
     */
    public List<Plato> obtenerPlatosPorRestaurante(Long restauranteId) {
        return platoRepository.findByRestauranteId(restauranteId);
    }

    /**
     * Modificar un plato existente.
     */
    public Plato modificarPlato(Long platoId, Long propietarioId, String nuevaDescripcion, Integer nuevoPrecio) {
        Plato plato = platoRepository.findById(platoId)
                .orElseThrow(() -> new IllegalArgumentException("El plato no existe"));

        verificarPropietario(plato.getRestaurante(), propietarioId);

        // Actualizar solo los campos permitidos
        plato.setDescripcion(nuevaDescripcion);
        plato.setPrecio(nuevoPrecio);

        return platoRepository.save(plato);
    }

    /**
     * Verificar que el restaurante exista.
     */
    private Restaurante verificarRestaurante(Long restauranteId) {
        return restauranteRepository.findById(restauranteId)
                .orElseThrow(() -> new IllegalArgumentException("Restaurante no encontrado."));
    }

    /**
     * Verificar que el usuario es el propietario del restaurante.
     */
    private void verificarPropietario(Restaurante restaurante, Long propietarioId) {
        UsuarioDTO propietario = restTemplate.getForObject(USUARIOS_SERVICE_URL, UsuarioDTO.class, propietarioId);

        if (propietario == null || !propietario.getRol().equals("PROPIETARIO") || !restaurante.getPropietarioId().equals(propietarioId)) {
            throw new IllegalArgumentException("El usuario no es propietario de este restaurante.");
        }
    }
}
