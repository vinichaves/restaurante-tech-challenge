package br.com.fiap.restaurante.restaurante.infrastructure.mapper.mapper;

import br.com.fiap.restaurante.restaurante.domain.model.Cliente;
import br.com.fiap.restaurante.restaurante.domain.model.Usuario;
import br.com.fiap.restaurante.restaurante.infrastructure.persistence.ClienteEntity;
import br.com.fiap.restaurante.restaurante.infrastructure.persistence.UsuarioEntity;
import br.com.fiap.restaurante.restaurante.web.dto.ClienteResponse;
import br.com.fiap.restaurante.restaurante.web.dto.UsuarioResponseDTO;

public class ClienteMapper {

    public static Cliente toDomain(ClienteEntity entity) {
        if (entity == null) return null;
        return new Cliente(
                entity.getId(),
                entity.getNome(),
                entity.getEmail(),
                entity.getCpf(),
                entity.getTelefone()
        );
    }

    public static ClienteEntity toEntity(Cliente domain) {
        if (domain == null) return null;

        return ClienteEntity.builder()
                .id(domain.getId())
                .nome(domain.getNome())
                .email(domain.getEmail())
                .cpf(domain.getCpf())
                .telefone(domain.getTelefone())
                .build();
    }

    public static ClienteResponse toResponseDTO(Cliente cliente) {
        return new ClienteResponse(
                cliente.getId(),
                cliente.getNome(),
                cliente.getEmail(),
                cliente.getCpf(),
                cliente.getTelefone()
        );
    }
}
