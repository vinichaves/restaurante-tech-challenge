package br.com.fiap.restaurante.restaurante.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemPedidoRepository extends JpaRepository<ItemPedidoEntity, Long> {
    List<ItemPedidoEntity> findByPedidoId(Long pedidoId);
}