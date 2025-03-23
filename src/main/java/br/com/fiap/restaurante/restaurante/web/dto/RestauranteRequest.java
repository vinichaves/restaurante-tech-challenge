package br.com.fiap.restaurante.restaurante.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RestauranteRequest {

    @NotBlank
    private String nome;

    @NotBlank
    private String endereco;

    @NotBlank
    private String tipoCozinha;

    @NotBlank
    private String horarioFuncionamento;

    @NotNull
    private Long idDono;
}