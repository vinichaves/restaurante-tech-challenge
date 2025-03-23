package br.com.fiap.restaurante.restaurante.infrastructure.mapper.mapper;

import br.com.fiap.restaurante.restaurante.domain.model.Usuario;
import br.com.fiap.restaurante.restaurante.infrastructure.persistence.UsuarioEntity;
import br.com.fiap.restaurante.restaurante.web.dto.UsuarioResponseDTO;

import java.security.cert.Extension;

public class UsuarioMapper {

    public static Usuario toDomain(UsuarioEntity entity) {
        if (entity == null) return null;
        return new Usuario(
                entity.getId(),
                entity.getNome(),
                entity.getEmail(),
                entity.getSenha()
        );
    }

    public static UsuarioEntity toEntity(Usuario domain) {
        if (domain == null) return null;

        return UsuarioEntity.builder()
                .id(domain.getId())
                .nome(domain.getNome())
                .email(domain.getEmail())
                .senha(domain.getSenha())
                .build();
    }

    public static UsuarioResponseDTO toResponseDTO(Usuario usuario) {
        return new UsuarioResponseDTO(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail()
        );
    }
}
