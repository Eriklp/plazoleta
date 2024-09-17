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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class RestauranteControllerTest {

    private MockMvc mockMvc;

    @Mock
    private RestauranteService restauranteService;

    @InjectMocks
    private RestauranteController restauranteController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(restauranteController).build();
    }

    @Test
    public void testCrearRestaurante() throws Exception {
        Restaurante restaurante = new Restaurante();
        restaurante.setNombre("Restaurante Test");
        restaurante.setNit("123456");
        restaurante.setDireccion("Calle Falsa 123");
        restaurante.setTelefono("+573005698325");
        restaurante.setPropietarioId(1L);

        when(restauranteService.crearRestaurante(any(Restaurante.class), anyLong())).thenReturn(restaurante);

        mockMvc.perform(post("/restaurantes/crear")
                        .param("idUsuario", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"nombre\": \"Restaurante Test\", \"nit\": \"123456\", \"direccion\": \"Calle Falsa 123\", \"telefono\": \"+573005698325\" }"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre").value("Restaurante Test"))
                .andExpect(jsonPath("$.nit").value("123456"));
    }
}
