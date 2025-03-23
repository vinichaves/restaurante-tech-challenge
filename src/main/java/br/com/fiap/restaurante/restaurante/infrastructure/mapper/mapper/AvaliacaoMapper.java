package br.com.fiap.restaurante.restaurante.infrastructure.mapper.mapper;

import br.com.fiap.restaurante.restaurante.domain.model.Avaliacao;
import br.com.fiap.restaurante.restaurante.infrastructure.persistence.AvaliacaoEntity;
import br.com.fiap.restaurante.restaurante.infrastructure.persistence.ClienteEntity;
import br.com.fiap.restaurante.restaurante.infrastructure.persistence.RestauranteEntity;
import br.com.fiap.restaurante.restaurante.web.dto.AvaliacaoResponse;


public class AvaliacaoMapper {

    public static Avaliacao toDomain(AvaliacaoEntity entity) {
        if (entity == null) return null;
        return new Avaliacao(
                entity.getId(),
                entity.getNota(),
                entity.getComentario(),
                entity.getData(),
                entity.getCliente().getId(),
                entity.getRestaurante().getId()
        );
    }

    public static AvaliacaoEntity toEntity(Avaliacao domain) {
        if (domain == null) return null;

        return AvaliacaoEntity.builder()
                .id(domain.getId())
                .nota(domain.getNota())
                .comentario(domain.getComentario())
                .data(domain.getData())
                .cliente(ClienteEntity.builder().id(domain.getIdCliente()).build())
                .restaurante(RestauranteEntity.builder().id(domain.getIdRestaurante()).build())
                .build();
    }

    public static AvaliacaoResponse toResponseDTO(Avaliacao avaliacao) {
        return new AvaliacaoResponse(
                avaliacao.getId(),
                avaliacao.getNota(),
                avaliacao.getComentario(),
                avaliacao.getData(),
                avaliacao.getIdCliente(),
                avaliacao.getIdRestaurante()
        );
    }
}
