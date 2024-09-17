package com.pragma.plazoleta.application.service;

import com.pragma.plazoleta.application.dto.UsuarioDTO;
import com.pragma.plazoleta.domain.model.Plato;
import com.pragma.plazoleta.domain.model.Restaurante;
import com.pragma.plazoleta.domain.repository.PlatoRepository;
import com.pragma.plazoleta.domain.repository.RestauranteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PlatoServiceTest {

    @InjectMocks
    private PlatoService platoService;

    @Mock
    private PlatoRepository platoRepository;

    @Mock
    private RestauranteRepository restauranteRepository;

    @Mock
    private RestTemplate restTemplate;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCrearPlato() {
        Long restauranteId = 1L;
        Long propietarioId = 1L;

        Restaurante restaurante = new Restaurante();
        restaurante.setId(restauranteId);
        restaurante.setPropietarioId(propietarioId);

        Plato plato = new Plato();
        plato.setNombre("Plato de prueba");
        plato.setPrecio(10000);
        plato.setDescripcion("Descripción de prueba");
        plato.setRestaurante(restaurante);

        // Simular la respuesta del microservicio de usuarios con el rol adecuado
        UsuarioDTO propietarioDTO = new UsuarioDTO();
        propietarioDTO.setRol("PROPIETARIO");

        // Simular las llamadas al repositorio y al RestTemplate
        when(restauranteRepository.findById(restauranteId)).thenReturn(Optional.of(restaurante));
        when(restTemplate.getForObject(anyString(), eq(UsuarioDTO.class), eq(propietarioId)))
                .thenReturn(propietarioDTO);
        when(platoRepository.save(any(Plato.class))).thenReturn(plato);

        // Ejecutar la prueba
        Plato resultado = platoService.crearPlato(plato, restauranteId, propietarioId);

        // Verificar que el plato fue creado correctamente
        assertNotNull(resultado);
        assertEquals("Plato de prueba", resultado.getNombre());
        verify(platoRepository).save(plato);
    }


    @Test
    void testModificarPlato() {
        Long platoId = 1L;
        Long propietarioId = 1L;

        Restaurante restaurante = new Restaurante();
        restaurante.setId(1L);
        restaurante.setPropietarioId(propietarioId);

        Plato plato = new Plato();
        plato.setId(platoId);
        plato.setNombre("Plato de prueba");
        plato.setPrecio(10000);
        plato.setDescripcion("Descripción de prueba");
        plato.setRestaurante(restaurante);

        // Simular la respuesta del microservicio de usuarios con el rol adecuado
        UsuarioDTO propietarioDTO = new UsuarioDTO();
        propietarioDTO.setRol("PROPIETARIO");

        // Simular la búsqueda y el guardado en el repositorio
        when(platoRepository.findById(platoId)).thenReturn(Optional.of(plato));
        when(restauranteRepository.findById(1L)).thenReturn(Optional.of(restaurante));
        when(restTemplate.getForObject(anyString(), eq(UsuarioDTO.class), eq(propietarioId)))
                .thenReturn(propietarioDTO);
        when(platoRepository.save(any(Plato.class))).thenReturn(plato);

        // Ejecutar la prueba
        Plato resultado = platoService.modificarPlato(platoId, propietarioId, "Nueva descripción", 15000);

        // Verificar que los valores fueron modificados correctamente y el resultado no es null
        assertNotNull(resultado);
        assertEquals("Nueva descripción", resultado.getDescripcion());
        assertEquals(15000, resultado.getPrecio());
        verify(platoRepository).save(plato);  // Verifica que el plato fue guardado correctamente
    }


}
