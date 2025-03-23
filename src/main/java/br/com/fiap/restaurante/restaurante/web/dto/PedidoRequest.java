package br.com.fiap.restaurante.restaurante.web.dto;


import br.com.fiap.restaurante.restaurante.domain.Enum.StatusPedido;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class PedidoRequest {

    private LocalDateTime date = LocalDateTime.now();

    @NotNull(message = "O status do pedido é obrigatório.")
    private StatusPedido status;

    @NotNull(message = "O ID do cliente é obrigatório.")
    @JsonProperty("idCliente")
    private Long idCliente;

    private List<ItemPedidoRequest> itens;
}
