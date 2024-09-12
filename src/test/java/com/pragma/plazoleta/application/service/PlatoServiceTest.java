package com.pragma.plazoleta.application.service;

import com.pragma.plazoleta.domain.model.Plato;
import com.pragma.plazoleta.domain.repository.PlatoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.List;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PlatoServiceTest {

    @InjectMocks
    private PlatoService platoService;

    @Mock
    private PlatoRepository platoRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCrearPlato() {
        Plato plato = new Plato();
        plato.setNombre("Plato A");

        when(platoRepository.save(plato)).thenReturn(plato);

        Plato creado = platoService.crearPlato(plato);
        assertNotNull(creado);
        assertEquals("Plato A", creado.getNombre());
        verify(platoRepository, times(1)).save(plato);
    }

    @Test
    public void testObtenerPlatosPorRestaurante() {
        Plato plato1 = new Plato();
        Plato plato2 = new Plato();
        when(platoRepository.findByRestauranteId(1L)).thenReturn(Arrays.asList(plato1, plato2));

        List<Plato> platos = platoService.obtenerPlatosPorRestaurante(1L);
        assertEquals(2, platos.size());
        verify(platoRepository, times(1)).findByRestauranteId(1L);
    }

    @Test
    public void testEliminarPlato() {
        platoService.eliminarPlato(1L);
        verify(platoRepository, times(1)).deleteById(1L);
    }
}
