package br.com.fiap.restaurante.restaurante.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ClienteRequest {

    @NotBlank(message = "O nome é obrigatório.")
    private String nome;

    @NotBlank(message = "O e-mail é obrigatório.")
    @Email(message = "E-mail inválido.")
    private String email;

    @NotBlank(message = "A cpf é obrigatório.")
    private String cpf;

    @NotBlank(message = "A telefone é obrigatório.")
    private String telefone;

}
