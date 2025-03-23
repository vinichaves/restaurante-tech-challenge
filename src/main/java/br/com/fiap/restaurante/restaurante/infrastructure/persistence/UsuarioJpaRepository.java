package br.com.fiap.restaurante.restaurante.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioJpaRepository extends JpaRepository <UsuarioEntity, Long> {

    boolean existsByEmail(String email);
}
