package br.com.fiap.restaurante.restaurante.application.service;


import br.com.fiap.restaurante.restaurante.domain.model.Avaliacao;
import br.com.fiap.restaurante.restaurante.infrastructure.mapper.mapper.AvaliacaoMapper;
import br.com.fiap.restaurante.restaurante.infrastructure.persistence.AvaliacaoRepository;
import br.com.fiap.restaurante.restaurante.infrastructure.persistence.ClienteEntity;
import br.com.fiap.restaurante.restaurante.infrastructure.persistence.RestauranteEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AvaliacaoService {

    private final AvaliacaoRepository repository;

    public AvaliacaoService(AvaliacaoRepository repository) {
        this.repository = repository;
    }

    public Avaliacao salvar(Avaliacao avaliacao) {
        if (avaliacao.getId() != null) {
            throw new IllegalArgumentException("Não envie ID ao criar uma nova avaliação.");
        }
        var entity = AvaliacaoMapper.toEntity(avaliacao);
        var saved = repository.save(entity);
        return AvaliacaoMapper.toDomain(saved);
    }

    public List<Avaliacao> listarTodasAvaliacoes() {
        return repository.findAll()
                .stream()
                .map(AvaliacaoMapper::toDomain)
                .collect(Collectors.toList());
    }

    public Avaliacao buscarPorId(Long id){
        return repository.findById(id)
                .map(AvaliacaoMapper::toDomain)
                .orElse(null);
    }

    public boolean deletar(Long id) {
        if (!repository.existsById(id)) return false;
        repository.deleteById(id);
        return true;
    }

    public Avaliacao atualizar(Long id, Avaliacao dadosAtualizados) {
        var existenteOpt = repository.findById(id);
        if (existenteOpt.isEmpty()) return null;

        if (
                        dadosAtualizados.getNota() == null &&
                        dadosAtualizados.getComentario() == null &&
                        dadosAtualizados.getData() == null &&
                        dadosAtualizados.getIdCliente() == null  &&
                       dadosAtualizados.getIdRestaurante() == null)
        {
            throw new IllegalArgumentException("Nenhum dado para atualizar.");
        }

        var existente = existenteOpt.get();

        if (dadosAtualizados.getNota() != null) {
            existente.setNota(dadosAtualizados.getNota());
        }
        if (dadosAtualizados.getComentario() != null) {
            existente.setComentario(dadosAtualizados.getComentario());
        }
        if (dadosAtualizados.getData() != null) {
            existente.setData(dadosAtualizados.getData());
        }
        if (dadosAtualizados.getIdCliente() != null) {
            existente.setCliente(ClienteEntity.builder().id(dadosAtualizados.getIdCliente()).build());
        }
        if (dadosAtualizados.getIdRestaurante() != null) {
            existente.setRestaurante(RestauranteEntity.builder().id(dadosAtualizados.getIdRestaurante()).build());
        }

        var atualizado = repository.save(existente);
        return AvaliacaoMapper.toDomain(atualizado);
    }
}
