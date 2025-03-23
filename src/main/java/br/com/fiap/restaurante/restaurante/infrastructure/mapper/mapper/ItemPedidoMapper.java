package br.com.fiap.restaurante.restaurante.infrastructure.mapper.mapper;

import br.com.fiap.restaurante.restaurante.domain.model.ItemPedido;
import br.com.fiap.restaurante.restaurante.domain.model.Pedido;
import br.com.fiap.restaurante.restaurante.infrastructure.persistence.ItemPedidoEntity;
import br.com.fiap.restaurante.restaurante.infrastructure.persistence.PedidoEntity;
import br.com.fiap.restaurante.restaurante.web.dto.ItemPedidoResponse;

public class ItemPedidoMapper {

    public static ItemPedido toDomain(ItemPedidoEntity entity) {
        if (entity == null) return null;

        var pedido = Pedido.builder()
                .id(entity.getPedido().getId())
                .build();

        return ItemPedido.builder()
                .id(entity.getId())
                .produto(entity.getProduto())
                .quantidade(entity.getQuantidade())
                .pedido(pedido)
                .build();
    }

    public static ItemPedidoEntity toEntity(ItemPedido domain) {
        if (domain == null) return null;

        var pedidoEntity = PedidoEntity.builder()
                .id(domain.getPedido().getId())
                .build();

        return ItemPedidoEntity.builder()
                .id(domain.getId())
                .produto(domain.getProduto())
                .quantidade(domain.getQuantidade())
                .pedido(pedidoEntity)
                .build();
    }

    public static ItemPedidoResponse toResponseDTO(ItemPedido itemPedido) {
        return new ItemPedidoResponse(
                itemPedido.getId(),
                itemPedido.getProduto(),
                itemPedido.getQuantidade(),
                itemPedido.getPedido().getId()
        );
    }
}