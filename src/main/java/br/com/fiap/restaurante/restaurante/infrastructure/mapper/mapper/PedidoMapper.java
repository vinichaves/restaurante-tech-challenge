package br.com.fiap.restaurante.restaurante.infrastructure.mapper.mapper;

import br.com.fiap.restaurante.restaurante.domain.model.Cliente;
import br.com.fiap.restaurante.restaurante.domain.model.ItemPedido;
import br.com.fiap.restaurante.restaurante.domain.model.Pedido;
import br.com.fiap.restaurante.restaurante.infrastructure.persistence.ClienteEntity;
import br.com.fiap.restaurante.restaurante.infrastructure.persistence.PedidoEntity;
import br.com.fiap.restaurante.restaurante.web.dto.ClienteResponse;
import br.com.fiap.restaurante.restaurante.web.dto.PedidoResponse;

import java.util.List;

public class PedidoMapper {

    public static Pedido toDomain(PedidoEntity entity) {
        if (entity == null) return null;
        return new Pedido(
                entity.getId(),
                entity.getDate(),
                entity.getStatus(),
                entity.getCliente().getId()
        );
    }

    public static PedidoEntity toEntity(Pedido domain) {
        if (domain == null) return null;

        var cliente = ClienteEntity.builder().id(domain.getIdCliente()).build();

        return PedidoEntity.builder()
                .id(domain.getId())
                .date(domain.getDate())
                .status(domain.getStatus())
                .cliente(cliente)
                .build();
    }

    public static PedidoResponse toResponseDTO(Pedido pedido, List<ItemPedido> itens) {
        return new PedidoResponse(
                pedido.getId(),
                pedido.getDate(),
                pedido.getStatus(),
                pedido.getIdCliente(),
                itens.stream()
                        .map(ItemPedidoMapper::toResponseDTO)
                        .toList()
        );
    }
}
