package br.com.fiap.restaurante.restaurante.infrastructure.mapper.mapper;

import br.com.fiap.restaurante.restaurante.domain.model.ItemCardapio;
import br.com.fiap.restaurante.restaurante.infrastructure.persistence.ItemCardapioEntity;
import br.com.fiap.restaurante.restaurante.infrastructure.persistence.RestauranteEntity;
import br.com.fiap.restaurante.restaurante.web.dto.ItemCardapioRequest;
import br.com.fiap.restaurante.restaurante.web.dto.ItemCardapioResponse;

public class ItemCardapioMapper {

    public static ItemCardapioEntity toEntity(ItemCardapio domain) {
        if (domain == null) return null;

        return ItemCardapioEntity.builder()
                .id(domain.getId())
                .nome(domain.getNome())
                .descricao(domain.getDescricao())
                .preco(domain.getPreco())
                .disponivelSomenteNoLocal(domain.isDisponivelSomenteNoLocal())
                .caminhoFoto(domain.getCaminhoFoto())
                .restaurante(RestauranteEntity.builder().id(domain.getIdRestaurante()).build())
                .build();
    }

    public static ItemCardapio toDomain(ItemCardapioEntity entity) {
        if (entity == null) return null;

        return new ItemCardapio(
                entity.getId(),
                entity.getNome(),
                entity.getDescricao(),
                entity.getPreco(),
                entity.getRestaurante() != null ? entity.getRestaurante().getId() : null,
                entity.isDisponivelSomenteNoLocal(),
                entity.getCaminhoFoto()
        );
    }

    public static ItemCardapio toDomain(ItemCardapioRequest request) {
        if (request == null) return null;

        return new ItemCardapio(
                null,
                request.nome(),
                request.descricao(),
                request.preco(),
                request.idRestaurante(),
                request.disponivelSomenteNoLocal(),
                request.caminhoFoto()
        );
    }

    public static ItemCardapioResponse toResponseDTO(ItemCardapio item) {
        if (item == null) return null;

        return new ItemCardapioResponse(
                item.getId(),
                item.getNome(),
                item.getDescricao(),
                item.getPreco(),
                item.getIdRestaurante(),
                item.isDisponivelSomenteNoLocal(),
                item.getCaminhoFoto()
        );
    }
}