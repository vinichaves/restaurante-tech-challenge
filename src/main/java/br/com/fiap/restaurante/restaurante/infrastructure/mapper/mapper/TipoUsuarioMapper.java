package br.com.fiap.restaurante.restaurante.infrastructure.mapper.mapper;


import br.com.fiap.restaurante.restaurante.infrastructure.persistence.TipoUsuarioEntity;
import br.com.fiap.restaurante.restaurante.web.dto.TipoUsuarioRequest;
import br.com.fiap.restaurante.restaurante.web.dto.TipoUsuarioResponse;

public class TipoUsuarioMapper {

    public static TipoUsuarioEntity toEntity(TipoUsuarioRequest request) {
        return TipoUsuarioEntity.builder()
                .nome(request.getNome())
                .build();
    }

    public static TipoUsuarioResponse toResponse(TipoUsuarioEntity entity) {
        return new TipoUsuarioResponse(entity.getId(), entity.getNome());
    }
}