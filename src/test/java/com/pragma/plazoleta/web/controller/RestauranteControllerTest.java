package com.pragma.plazoleta.web.controller;

import com.pragma.plazoleta.application.service.RestauranteService;
import com.pragma.plazoleta.domain.model.Restaurante;
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

public class RestauranteControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private RestauranteController restauranteController;

    @Mock
    private RestauranteService restauranteService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(restauranteController).build();
    }

    @Test
    public void testCrearRestaurante() throws Exception {
        Restaurante restaurante = new Restaurante();
        restaurante.setNombre("Restaurante A");

        when(restauranteService.crearRestaurante(any(Restaurante.class), eq(1L))).thenReturn(restaurante);

        mockMvc.perform(post("/api/restaurantes")
                        .param("propietarioId", "1")  // Pasar el propietarioId como parámetro en la URL
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nombre\":\"Restaurante A\",\"direccion\":\"Dirección A\",\"nit\":\"12345678\",\"telefono\":\"+573005698325\",\"urlLogo\":\"https://logo.com/logo.png\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Restaurante A"));

        verify(restauranteService, times(1)).crearRestaurante(any(Restaurante.class), eq(1L));
    }
}
