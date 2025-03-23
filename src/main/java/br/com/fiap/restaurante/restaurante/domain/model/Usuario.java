package br.com.fiap.restaurante.restaurante.domain.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    private Long id;
    private String nome;
    private String email;
    private String senha;
}