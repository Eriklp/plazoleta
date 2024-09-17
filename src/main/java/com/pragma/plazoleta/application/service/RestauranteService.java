package com.pragma.plazoleta.application.service;

import com.pragma.plazoleta.application.dto.UsuarioDTO;
import com.pragma.plazoleta.config.UsuarioClient;
import com.pragma.plazoleta.domain.model.Restaurante;
import com.pragma.plazoleta.domain.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RestauranteService {

    @Autowired
    private UsuarioClient usuarioClient;

    @Autowired
    private RestauranteRepository restauranteRepository;

    public Restaurante crearRestaurante(Restaurante restaurante, Long idUsuario) {
        // Validar que el usuario es un administrador
        boolean esAdmin = usuarioClient.validarRolUsuario(idUsuario, "ADMINISTRADOR");
        if (!esAdmin) {
            throw new IllegalArgumentException("El usuario no tiene permisos para crear un restaurante.");
        }

        // Guardar el restaurante
        return restauranteRepository.save(restaurante);
    }

    public UsuarioDTO obtenerUsuarioPorId(Long idUsuario) {
        return usuarioClient.obtenerUsuarioPorId(idUsuario);
    }
}