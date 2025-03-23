package br.com.fiap.restaurante.restaurante.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AvaliacaoRepository extends JpaRepository<AvaliacaoEntity, Long> {

    List<AvaliacaoEntity> findByRestauranteId(Long restauranteId);
}
