package com.pragma.plazoleta.domain.repository;

import com.pragma.plazoleta.domain.model.Plato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlatoRepository extends JpaRepository<Plato, Long> {
    List<Plato> findByRestauranteId(Long restauranteId);
}
