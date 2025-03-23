package br.com.fiap.restaurante.restaurante.web.dto;

public record RestauranteResponse(
        Long id,
        String nome,
        String endereco,
        String tipoCozinha,
        String horarioFuncionamento,
        Long idDono
) {}
