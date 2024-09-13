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
    public void testCrearPlatoConPropietarioValido() {
        Plato plato = new Plato();
        plato.setNombre("Plato A");
        plato.setPrecio(100);
        plato.setDescripcion("Descripción A");

        Restaurante restaurante = new Restaurante();
        restaurante.setId(1L);
        restaurante.setPropietarioId(1L);  // Establecer el propietarioId en el restaurante

        UsuarioDTO propietario = new UsuarioDTO();
        propietario.setId(1L);
        propietario.setRol("PROPIETARIO");

        when(restauranteRepository.findById(1L)).thenReturn(Optional.of(restaurante));
        when(restTemplate.getForObject(anyString(), eq(UsuarioDTO.class), eq(1L))).thenReturn(propietario);
        when(platoRepository.save(any(Plato.class))).thenReturn(plato);

        // Llamada al método con los tres parámetros requeridos
        Plato creado = platoService.crearPlato(plato, 1L, 1L);

        // Verificaciones
        assertNotNull(creado);
        assertEquals("Plato A", creado.getNombre());
        verify(platoRepository, times(1)).save(plato);
    }

    @Test
    void testModificarPlato() {
        Long platoId = 1L;
        Long propietarioId = 1L;
        Plato plato = new Plato();
        plato.setId(platoId);
        plato.setDescripcion("Descripción antigua");
        plato.setPrecio(10000);
        Restaurante restaurante = new Restaurante();
        restaurante.setId(1L);
        restaurante.setPropietarioId(propietarioId);
        plato.setRestaurante(restaurante);

        // Simular que el plato existe en el repositorio
        when(platoRepository.findById(platoId)).thenReturn(Optional.of(plato));

        // Simular que el plato se guarda en el repositorio y devuelve el plato modificado
        when(platoRepository.save(any(Plato.class))).thenReturn(plato);

        // Ejecutar el método de modificación
        Plato platoModificado = platoService.modificarPlato(platoId, propietarioId, "Nueva descripción", 12000);

        // Verificar que los cambios se aplicaron correctamente
        assertNotNull(platoModificado);  // Asegurarse de que el plato no es null
        assertEquals("Nueva descripción", platoModificado.getDescripcion());
        assertEquals(12000, platoModificado.getPrecio());

        // Verificar que el método save() fue llamado
        verify(platoRepository).save(plato);
    }

}
