package br.com.fiap.restaurante.restaurante.infrastructure.mapper.mapper;

import br.com.fiap.restaurante.restaurante.domain.model.ItemPedido;
import br.com.fiap.restaurante.restaurante.domain.model.Pedido;
import br.com.fiap.restaurante.restaurante.domain.model.Restaurante;
import br.com.fiap.restaurante.restaurante.infrastructure.persistence.ClienteEntity;
import br.com.fiap.restaurante.restaurante.infrastructure.persistence.PedidoEntity;
import br.com.fiap.restaurante.restaurante.infrastructure.persistence.RestauranteEntity;
import br.com.fiap.restaurante.restaurante.infrastructure.persistence.UsuarioEntity;
import br.com.fiap.restaurante.restaurante.web.dto.PedidoResponse;
import br.com.fiap.restaurante.restaurante.web.dto.RestauranteResponse;

import java.util.List;

public class RestauranteMapper {

    public static Restaurante toDomain(RestauranteEntity entity) {
        if (entity == null) return null;
        return new Restaurante(
                entity.getId(),
                entity.getNome(),
                entity.getEndereco(),
                entity.getTipoCozinha(),
                entity.getHorarioFuncionamento(),
                entity.getDono().getId());
    }

    public static RestauranteEntity toEntity(Restaurante domain) {
        if (domain == null) return null;

        var dono = UsuarioEntity.builder().id(domain.getIdDono()).build(); // CORRETO

        return RestauranteEntity.builder()
                .id(domain.getId())
                .nome(domain.getNome())
                .endereco(domain.getEndereco())
                .tipoCozinha(domain.getTipoCozinha())
                .horarioFuncionamento(domain.getHorarioFuncionamento())
                .dono(dono)
                .build();
    }

    public static RestauranteResponse toResponseDTO(Restaurante restaurante) {
        return new RestauranteResponse(
                restaurante.getId(),
                restaurante.getNome(),
                restaurante.getEndereco(),
                restaurante.getTipoCozinha(),
                restaurante.getHorarioFuncionamento(),
                restaurante.getIdDono());
    }
}
