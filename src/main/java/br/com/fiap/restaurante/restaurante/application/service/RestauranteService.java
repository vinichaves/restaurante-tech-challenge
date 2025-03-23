package br.com.fiap.restaurante.restaurante.application.service;

import br.com.fiap.restaurante.restaurante.domain.model.Pedido;
import br.com.fiap.restaurante.restaurante.domain.model.Restaurante;
import br.com.fiap.restaurante.restaurante.infrastructure.mapper.mapper.PedidoMapper;
import br.com.fiap.restaurante.restaurante.infrastructure.mapper.mapper.RestauranteMapper;
import br.com.fiap.restaurante.restaurante.infrastructure.persistence.PedidoRepository;
import br.com.fiap.restaurante.restaurante.infrastructure.persistence.RestauranteRepository;
import br.com.fiap.restaurante.restaurante.infrastructure.persistence.UsuarioEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class RestauranteService {

    private final RestauranteRepository repository;

    public RestauranteService(RestauranteRepository repository) {
        this.repository = repository;
    }

    public Restaurante salvar(Restaurante restaurante) {
        if (restaurante.getId() != null) {
            throw new IllegalArgumentException("Não envie ID ao criar um novo restaurante.");
        }
        var entity = RestauranteMapper.toEntity(restaurante);
        var saved = repository.save(entity);
        return RestauranteMapper.toDomain(saved);
    }

    public List<Restaurante> listarTodosRestaurantes() {
        return repository.findAll()
                .stream()
                .map(RestauranteMapper::toDomain)
                .collect(Collectors.toList());
    }

    public Restaurante buscarPorId(Long id){
        return repository.findById(id)
                .map(RestauranteMapper::toDomain)
                .orElse(null);
    }

    public boolean deletar(Long id) {
        if (!repository.existsById(id)) return false;
        repository.deleteById(id);
        return true;
    }

    public Restaurante atualizar(Long id, Restaurante dadosAtualizados) {
        var existenteOpt = repository.findById(id);
        if (existenteOpt.isEmpty()) return null;

        if (
                        dadosAtualizados.getNome() == null &&
                        dadosAtualizados.getEndereco() == null &&
                        dadosAtualizados.getTipoCozinha() == null &&
                        dadosAtualizados.getHorarioFuncionamento() == null &&
                        dadosAtualizados.getIdDono() == null
        )
            throw new IllegalArgumentException("Nenhum dado para atualizar.");

        var existente = existenteOpt.get();

        if (dadosAtualizados.getNome() != null) {
            existente.setNome(dadosAtualizados.getNome());
        }
        if (dadosAtualizados.getEndereco() != null) {
            existente.setEndereco(dadosAtualizados.getEndereco());
        }
        if (dadosAtualizados.getTipoCozinha() != null) {
            existente.setTipoCozinha(dadosAtualizados.getTipoCozinha());
        }
        if (dadosAtualizados.getHorarioFuncionamento() != null) {
            existente.setHorarioFuncionamento(dadosAtualizados.getHorarioFuncionamento());
        }
        if (dadosAtualizados.getIdDono() != null) {
            var dono = new UsuarioEntity();
            dono.setId(dadosAtualizados.getIdDono());
            existente.setDono(dono);
        }

        var atualizado = repository.save(existente);
        return RestauranteMapper.toDomain(atualizado);
    }
}
