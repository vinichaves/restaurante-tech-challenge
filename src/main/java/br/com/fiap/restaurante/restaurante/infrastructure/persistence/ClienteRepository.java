package br.com.fiap.restaurante.restaurante.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<ClienteEntity, Long> {
}
