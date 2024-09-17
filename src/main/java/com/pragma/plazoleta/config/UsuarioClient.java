package com.pragma.plazoleta.config;

import com.pragma.plazoleta.application.dto.UsuarioDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class UsuarioClient {

    @Autowired
    private RestTemplate restTemplate;

    private final String USUARIO_SERVICE_URL = "http://usuarios:8080";

    public UsuarioDTO obtenerUsuarioPorId(Long idUsuario) {
        String url = USUARIO_SERVICE_URL + "/usuarios/" + idUsuario;
        return restTemplate.getForObject(url, UsuarioDTO.class);
    }

    public boolean validarRolUsuario(Long idUsuario, String rol) {
        String url = USUARIO_SERVICE_URL + "/usuarios/validar-rol?id=" + idUsuario + "&rol=" + rol;
        return restTemplate.getForObject(url, Boolean.class);
    }
}