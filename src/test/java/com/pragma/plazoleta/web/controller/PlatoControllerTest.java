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

        when(platoService.crearPlato(any(Plato.class), eq(1L), eq(1L))).thenReturn(plato);

        mockMvc.perform(post("/platos/crear")
                        .param("restauranteId", "1")  // Pasar el restauranteId como parámetro en la URL
                        .param("propietarioId", "1")  // Pasar el propietarioId como parámetro en la URL
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nombre\":\"Plato A\",\"precio\":25,\"descripcion\":\"Delicioso plato\",\"urlImagen\":\"https://image.com/plato.png\",\"categoria\":\"Carne\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre").value("Plato A"));

        verify(platoService, times(1)).crearPlato(any(Plato.class), eq(1L), eq(1L));
    }

}
