package br.com.fiap.restaurante.restaurante.web.dto;

import jakarta.validation.constraints.*;

public record ItemCardapioRequest(
        @NotBlank String nome,
        String descricao,
        @NotNull @DecimalMin("0.0") Double preco,
        @NotNull Long idRestaurante,
        boolean disponivelSomenteNoLocal,
        String caminhoFoto
) {}