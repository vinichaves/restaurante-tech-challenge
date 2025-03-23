package br.com.fiap.restaurante.restaurante.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Avaliacao {

    private Long id;
    private Integer nota;
    private String comentario;
    private LocalDateTime data;
    private Long idCliente;
    private Long idRestaurante;
}