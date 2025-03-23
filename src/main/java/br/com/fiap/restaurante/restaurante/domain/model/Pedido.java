package br.com.fiap.restaurante.restaurante.domain.model;

import br.com.fiap.restaurante.restaurante.domain.Enum.StatusPedido;
import lombok.*;


import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pedido {

    private Long id;
    private LocalDateTime date;
    private StatusPedido status;
    private Long idCliente;
}
