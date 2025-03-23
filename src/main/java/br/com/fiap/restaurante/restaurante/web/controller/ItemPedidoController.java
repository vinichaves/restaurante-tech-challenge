package br.com.fiap.restaurante.restaurante.web.controller;

import br.com.fiap.restaurante.restaurante.application.service.ItemPedidoService;
import br.com.fiap.restaurante.restaurante.domain.model.ItemPedido;
import br.com.fiap.restaurante.restaurante.infrastructure.mapper.mapper.ItemPedidoMapper;
import br.com.fiap.restaurante.restaurante.web.dto.ItemPedidoRequest;
import br.com.fiap.restaurante.restaurante.web.dto.ItemPedidoResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/item-pedido")
public class ItemPedidoController {

    private final ItemPedidoService itemPedidoService;

    public ItemPedidoController(ItemPedidoService itemPedidoService) {
        this.itemPedidoService = itemPedidoService;
    }

    @PostMapping
    public ResponseEntity<ItemPedidoResponse> salvar(@RequestBody @Valid ItemPedidoRequest request) {
        var pedido = itemPedidoService.buscarPedidoPorId(request.getIdPedido());
        var item = new ItemPedido(null, request.getProduto(), request.getQuantidade(), pedido);
        var salvo = itemPedidoService.salvar(item);
        return ResponseEntity.ok(ItemPedidoMapper.toResponseDTO(salvo));
    }

    @GetMapping
    public ResponseEntity<List<ItemPedidoResponse>> listarTodos() {
        var lista = itemPedidoService.listarTodosPedidos()
                .stream()
                .map(ItemPedidoMapper::toResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemPedidoResponse> buscarPorId(@PathVariable Long id) {
        var item = itemPedidoService.buscarPorId(id);
        return ResponseEntity.ok(ItemPedidoMapper.toResponseDTO(item));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ItemPedidoResponse> atualizar(@PathVariable Long id, @RequestBody @Valid ItemPedidoRequest request) {
        var pedido = itemPedidoService.buscarPedidoPorId(request.getIdPedido());
        var atualizado = new ItemPedido(id, request.getProduto(), request.getQuantidade(), pedido);
        var salvo = itemPedidoService.atualizar(id, atualizado);
        return ResponseEntity.ok(ItemPedidoMapper.toResponseDTO(salvo));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        itemPedidoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}