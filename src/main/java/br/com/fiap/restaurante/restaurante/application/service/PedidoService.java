package br.com.fiap.restaurante.restaurante.application.service;

import br.com.fiap.restaurante.restaurante.domain.model.Pedido;
import br.com.fiap.restaurante.restaurante.infrastructure.mapper.mapper.PedidoMapper;
import br.com.fiap.restaurante.restaurante.infrastructure.persistence.ClienteEntity;
import br.com.fiap.restaurante.restaurante.infrastructure.persistence.PedidoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PedidoService {

    private final PedidoRepository repository;

    public PedidoService(PedidoRepository repository) {
        this.repository = repository;
    }

    public Pedido salvar(Pedido pedido) {
        if (pedido.getId() != null) {
            throw new IllegalArgumentException("Não envie ID ao criar um novo pedido.");
        }
        var entity = PedidoMapper.toEntity(pedido);
        var saved = repository.save(entity);
        return PedidoMapper.toDomain(saved);
    }

    public List<Pedido> listarTodosPedidos() {
        return repository.findAll()
                .stream()
                .map(PedidoMapper::toDomain)
                .collect(Collectors.toList());
    }

    public Pedido buscarPorId(Long id) {
        return repository.findById(id)
                .map(PedidoMapper::toDomain)
                .orElse(null);
    }

    public boolean deletar(Long id) {
        if (!repository.existsById(id)) return false;
        repository.deleteById(id); // Agora os itens são apagados automaticamente
        return true;
    }

    public Pedido atualizar(Long id, Pedido dadosAtualizados) {
        var existenteOpt = repository.findById(id);
        if (existenteOpt.isEmpty()) return null;

        if (
                dadosAtualizados.getDate() == null &&
                        dadosAtualizados.getStatus() == null &&
                        dadosAtualizados.getIdCliente() == null
        ) {
            throw new IllegalArgumentException("Nenhum dado para atualizar.");
        }

        var existente = existenteOpt.get();

        if (dadosAtualizados.getDate() != null) {
            existente.setDate(dadosAtualizados.getDate());
        }
        if (dadosAtualizados.getStatus() != null) {
            existente.setStatus(dadosAtualizados.getStatus());
        }
        if (dadosAtualizados.getIdCliente() != null) {
            existente.setCliente(ClienteEntity.builder().id(dadosAtualizados.getIdCliente()).build());
        }

        var atualizado = repository.save(existente);
        return PedidoMapper.toDomain(atualizado);
    }
}