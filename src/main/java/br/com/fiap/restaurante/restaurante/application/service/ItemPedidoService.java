package br.com.fiap.restaurante.restaurante.application.service;

import br.com.fiap.restaurante.restaurante.config.RecursoNaoEncontradoException;
import br.com.fiap.restaurante.restaurante.domain.model.ItemPedido;

import br.com.fiap.restaurante.restaurante.domain.model.Pedido;
import br.com.fiap.restaurante.restaurante.infrastructure.mapper.mapper.ItemPedidoMapper;
import br.com.fiap.restaurante.restaurante.infrastructure.persistence.ItemPedidoRepository;
import br.com.fiap.restaurante.restaurante.infrastructure.persistence.PedidoRepository;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemPedidoService {

    private final ItemPedidoRepository repository;
    private final PedidoRepository pedidoRepository;

    public ItemPedidoService(ItemPedidoRepository repository, PedidoRepository pedidoRepository) {
        this.repository = repository;
        this.pedidoRepository = pedidoRepository;
    }

    public ItemPedido salvar(ItemPedido itemPedido) {
        if (itemPedido.getId() != null) {
            throw new IllegalArgumentException("Não envie ID ao criar um novo pedido.");
        }
        var entity = ItemPedidoMapper.toEntity(itemPedido);
        var saved = repository.save(entity);
        return ItemPedidoMapper.toDomain(saved);
    }

    public List<ItemPedido> listarTodosPedidos() {
        return repository.findAll()
                .stream()
                .map(ItemPedidoMapper::toDomain)
                .collect(Collectors.toList());
    }

    public List<ItemPedido> buscarPorIdPedido(Long pedidoId) {
        return repository.findByPedidoId(pedidoId)
                .stream()
                .map(ItemPedidoMapper::toDomain)
                .collect(Collectors.toList());
    }

    public ItemPedido buscarPorId(Long id) {
        return repository.findById(id)
                .map(ItemPedidoMapper::toDomain)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Item do pedido não encontrado"));
    }

    public boolean deletar(Long id) {
        if (!repository.existsById(id)) return false;
        repository.deleteById(id);
        return true;
    }

    public ItemPedido atualizar(Long id, ItemPedido dadosAtualizados) {
        var existente = repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Item do pedido não encontrado"));

        if (dadosAtualizados.getProduto() == null && dadosAtualizados.getQuantidade() == null) {
            throw new IllegalArgumentException("Nenhum dado para atualizar.");
        }

        if (dadosAtualizados.getProduto() != null) {
            existente.setProduto(dadosAtualizados.getProduto());
        }
        if (dadosAtualizados.getQuantidade() != null) {
            existente.setQuantidade(dadosAtualizados.getQuantidade());
        }

        var atualizado = repository.save(existente);
        return ItemPedidoMapper.toDomain(atualizado);
    }

    public Pedido buscarPedidoPorId(Long idPedido) {
        return pedidoRepository.findById(idPedido)
                .map(entity -> Pedido.builder()
                        .id(entity.getId())
                        .date(entity.getDate())
                        .status(entity.getStatus())
                        .idCliente(entity.getCliente().getId())
                        .build()
                )
                .orElseThrow(() -> new RecursoNaoEncontradoException("Pedido não encontrado"));
    }
}