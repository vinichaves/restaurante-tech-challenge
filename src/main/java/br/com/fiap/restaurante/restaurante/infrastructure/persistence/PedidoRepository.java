package br.com.fiap.restaurante.restaurante.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository  extends JpaRepository<PedidoEntity, Long> {
}
