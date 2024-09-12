package com.pragma.plazoleta.web.controller;

import com.pragma.plazoleta.application.service.PlatoService;
import com.pragma.plazoleta.domain.model.Plato;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;
import java.util.Arrays;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class PlatoControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private PlatoController platoController;

    @Mock
    private PlatoService platoService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(platoController).build();
    }

    @Test
    public void testCrearPlato() throws Exception {
        Plato plato = new Plato();
        plato.setNombre("Plato A");

        when(platoService.crearPlato(any(Plato.class))).thenReturn(plato);

        mockMvc.perform(post("/api/platos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nombre\":\"Plato A\",\"precio\":25.0}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Plato A"));

        verify(platoService, times(1)).crearPlato(any(Plato.class));
    }

    @Test
    public void testObtenerPlatosPorRestaurante() throws Exception {
        Plato plato1 = new Plato();
        Plato plato2 = new Plato();
        when(platoService.obtenerPlatosPorRestaurante(1L)).thenReturn(Arrays.asList(plato1, plato2));

        mockMvc.perform(get("/api/platos/restaurante/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));

        verify(platoService, times(1)).obtenerPlatosPorRestaurante(1L);
    }

    @Test
    public void testEliminarPlato() throws Exception {
        mockMvc.perform(delete("/api/platos/1"))
                .andExpect(status().isNoContent());

        verify(platoService, times(1)).eliminarPlato(1L);
    }
}
