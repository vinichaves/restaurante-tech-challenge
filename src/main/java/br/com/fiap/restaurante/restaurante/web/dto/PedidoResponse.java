package br.com.fiap.restaurante.restaurante.web.dto;

import br.com.fiap.restaurante.restaurante.domain.Enum.StatusPedido;

import java.time.LocalDateTime;
import java.util.List;

public record PedidoResponse(
        Long id,
        LocalDateTime date,
        StatusPedido status,
        Long idCliente,
        List<ItemPedidoResponse> itens
) {
}
