package br.com.fiap.restaurante.restaurante.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ItemPedidoRequest {

    @NotBlank(message = "O nome do produto é obrigatório.")
    private String produto;

    @NotNull(message = "A quantidade é obrigatória.")
    private Long quantidade;

    @NotNull(message = "O ID do pedido é obrigatório.")
    private Long idPedido;
}