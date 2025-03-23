package br.com.fiap.restaurante.restaurante.web.dto;

public record ItemPedidoResponse(Long id, String produto, Long quantidade, Long idPedido) {
}
