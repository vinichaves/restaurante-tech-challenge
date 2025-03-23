package br.com.fiap.restaurante.restaurante.web.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AvaliacaoRequest {

    @NotNull(message = "A nota é obrigatória.")
    @Min(value = 1, message = "A nota mínima é 1.")
    @Max(value = 5, message = "A nota máxima é 5.")
    private Integer nota;

    private String comentario;
    private LocalDateTime data;

    @NotNull(message = "O ID do cliente é obrigatório.")
    private Long idCliente;

    @NotNull(message = "O ID do restaurante é obrigatório.")
    private Long idRestaurante;
}