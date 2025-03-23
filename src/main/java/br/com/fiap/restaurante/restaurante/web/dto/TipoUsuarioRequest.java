package br.com.fiap.restaurante.restaurante.web.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TipoUsuarioRequest {

    @NotBlank(message = "O nome é obrigatório.")
    private String nome;
}