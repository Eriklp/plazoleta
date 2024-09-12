package com.pragma.plazoleta.application.service;

import com.pragma.plazoleta.domain.model.Restaurante;
import com.pragma.plazoleta.domain.repository.RestauranteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class RestauranteServiceTest {

    @InjectMocks
    private RestauranteService restauranteService;

    @Mock
    private RestauranteRepository restauranteRepository;

    @Mock
    private RestTemplate restTemplate;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCrearRestauranteConPropietarioValido() {
        Restaurante restaurante = new Restaurante();
        restaurante.setNombre("Restaurante A");

        UsuarioDTO propietario = new UsuarioDTO();
        propietario.setId(1L);
        propietario.setRol("PROPIETARIO");

        when(restTemplate.getForObject(anyString(), eq(UsuarioDTO.class), eq(1L))).thenReturn(propietario);
        when(restauranteRepository.save(any(Restaurante.class))).thenReturn(restaurante);

        Restaurante creado = restauranteService.crearRestaurante(restaurante, 1L);
        assertNotNull(creado);
        assertEquals("Restaurante A", creado.getNombre());
        verify(restauranteRepository, times(1)).save(restaurante);
    }

    @Test
    public void testCrearRestauranteConPropietarioInvalido() {
        Restaurante restaurante = new Restaurante();
        restaurante.setNombre("Restaurante A");

        when(restTemplate.getForObject(anyString(), eq(UsuarioDTO.class), eq(1L))).thenReturn(null);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            restauranteService.crearRestaurante(restaurante, 1L);
        });

        assertEquals("El usuario con ID 1 no es un propietario válido.", exception.getMessage());
        verify(restauranteRepository, never()).save(any(Restaurante.class));
    }
}
