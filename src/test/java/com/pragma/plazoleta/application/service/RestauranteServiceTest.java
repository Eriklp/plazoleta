package com.pragma.plazoleta.application.service;

import com.pragma.plazoleta.domain.model.Restaurante;
import com.pragma.plazoleta.domain.repository.RestauranteRepository;
import com.pragma.plazoleta.config.UsuarioClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class RestauranteServiceTest {

    @Mock
    private RestauranteRepository restauranteRepository;

    @Mock
    private UsuarioClient usuarioClient;

    @InjectMocks
    private RestauranteService restauranteService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCrearRestaurante() {
        Restaurante restaurante = new Restaurante();
        restaurante.setNombre("Restaurante Test");
        restaurante.setNit("123456");
        restaurante.setDireccion("Calle Falsa 123");
        restaurante.setTelefono("+573005698325");
        restaurante.setPropietarioId(1L);

        // Mock the UsuarioClient to avoid NullPointerException
        when(usuarioClient.validarRolUsuario(anyLong(), anyString())).thenReturn(true);
        when(restauranteRepository.save(restaurante)).thenReturn(restaurante);

        Restaurante result = restauranteService.crearRestaurante(restaurante, 1L);
        assertNotNull(result);
    }
}
