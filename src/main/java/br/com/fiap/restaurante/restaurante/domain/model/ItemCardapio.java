package br.com.fiap.restaurante.restaurante.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemCardapio {
    private Long id;
    private String nome;
    private String descricao;
    private Double preco;
    private Long idRestaurante;
    private boolean disponivelSomenteNoLocal;
    private String caminhoFoto;
}