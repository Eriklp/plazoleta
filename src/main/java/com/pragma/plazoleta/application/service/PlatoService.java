package com.pragma.plazoleta.application.service;

import com.pragma.plazoleta.domain.model.Plato;
import com.pragma.plazoleta.domain.repository.PlatoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlatoService {

    @Autowired
    private PlatoRepository platoRepository;

    public Plato crearPlato(Plato plato) {
        return platoRepository.save(plato);
    }

    public List<Plato> obtenerPlatosPorRestaurante(Long restauranteId) {
        return platoRepository.findByRestauranteId(restauranteId);
    }

    public void eliminarPlato(Long id) {
        platoRepository.deleteById(id);
    }
}
