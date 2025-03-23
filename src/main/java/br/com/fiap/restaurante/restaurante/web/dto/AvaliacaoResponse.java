package br.com.fiap.restaurante.restaurante.web.dto;

import java.time.LocalDateTime;

public record AvaliacaoResponse (
        Long id,
        Integer nota,
        String comentario,
        LocalDateTime data,
        Long idCliente,
        Long idRestaurante){
}
