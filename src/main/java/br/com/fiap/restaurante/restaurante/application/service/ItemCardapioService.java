package br.com.fiap.restaurante.restaurante.application.service;

import br.com.fiap.restaurante.restaurante.domain.model.ItemCardapio;
import br.com.fiap.restaurante.restaurante.infrastructure.mapper.mapper.ItemCardapioMapper;
import br.com.fiap.restaurante.restaurante.infrastructure.persistence.ItemCardapioRepository;
import br.com.fiap.restaurante.restaurante.infrastructure.persistence.RestauranteEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemCardapioService {

    private final ItemCardapioRepository repository;

    public ItemCardapioService(ItemCardapioRepository repository) {
        this.repository = repository;
    }

    public ItemCardapio salvar(ItemCardapio item) {
        if (item.getId() != null) {
            throw new IllegalArgumentException("Não envie ID ao criar um novo item de cardápio.");
        }

        var entity = ItemCardapioMapper.toEntity(item);
        var salvo = repository.save(entity);
        return ItemCardapioMapper.toDomain(salvo);
    }

    public List<ItemCardapio> listarTodos() {
        return repository.findAll().stream()
                .map(ItemCardapioMapper::toDomain)
                .collect(Collectors.toList());
    }

    public ItemCardapio buscarPorId(Long id) {
        return repository.findById(id)
                .map(ItemCardapioMapper::toDomain)
                .orElse(null);
    }

    public boolean deletar(Long id) {
        if (!repository.existsById(id)) return false;
        repository.deleteById(id);
        return true;
    }

    public ItemCardapio atualizar(Long id, ItemCardapio dadosAtualizados) {
        var existenteOpt = repository.findById(id);
        if (existenteOpt.isEmpty()) return null;

        var existente = existenteOpt.get();

        if (dadosAtualizados.getNome() != null) {
            existente.setNome(dadosAtualizados.getNome());
        }
        if (dadosAtualizados.getDescricao() != null) {
            existente.setDescricao(dadosAtualizados.getDescricao());
        }
        if (dadosAtualizados.getPreco() != null) {
            existente.setPreco(dadosAtualizados.getPreco());
        }
        if (dadosAtualizados.getIdRestaurante() != null) {
            existente.setRestaurante(RestauranteEntity.builder()
                    .id(dadosAtualizados.getIdRestaurante())
                    .build());
        }

        existente.setDisponivelSomenteNoLocal(dadosAtualizados.isDisponivelSomenteNoLocal());

        if (dadosAtualizados.getCaminhoFoto() != null) {
            existente.setCaminhoFoto(dadosAtualizados.getCaminhoFoto());
        }

        var atualizado = repository.save(existente);
        return ItemCardapioMapper.toDomain(atualizado);
    }
}