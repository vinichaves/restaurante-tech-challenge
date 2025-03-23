package br.com.fiap.restaurante.restaurante.web.dto;

public record ItemCardapioResponse(
        Long id,
        String nome,
        String descricao,
        Double preco,
        Long idRestaurante,
        boolean disponivelSomenteNoLocal,
        String caminhoFoto
) {}