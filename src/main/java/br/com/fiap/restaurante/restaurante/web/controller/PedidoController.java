package br.com.fiap.restaurante.restaurante.web.controller;

import br.com.fiap.restaurante.restaurante.application.service.ItemPedidoService;
import br.com.fiap.restaurante.restaurante.application.service.PedidoService;
import br.com.fiap.restaurante.restaurante.domain.model.ItemPedido;
import br.com.fiap.restaurante.restaurante.domain.model.Pedido;
import br.com.fiap.restaurante.restaurante.infrastructure.mapper.mapper.PedidoMapper;
import br.com.fiap.restaurante.restaurante.web.dto.PedidoRequest;
import br.com.fiap.restaurante.restaurante.web.dto.PedidoResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/pedido")
public class PedidoController {

    private final PedidoService pedidoService;
    private final ItemPedidoService itemPedidoService;

    public PedidoController(PedidoService pedidoService, ItemPedidoService itemPedidoService) {
        this.pedidoService = pedidoService;
        this.itemPedidoService = itemPedidoService;
    }

    @PostMapping
    public ResponseEntity<PedidoResponse> salvar(@RequestBody @Valid PedidoRequest request) {
        var pedido = new Pedido(null, request.getDate(), request.getStatus(), request.getIdCliente());
        var pedidoSalvo = pedidoService.salvar(pedido);

        List<ItemPedido> itensSalvos = new ArrayList<>();
        if (request.getItens() != null && !request.getItens().isEmpty()) {
            for (var item : request.getItens()) {
                var itemPedido = new ItemPedido(null, item.getProduto(), item.getQuantidade(), pedidoSalvo);
                var salvo = itemPedidoService.salvar(itemPedido);
                itensSalvos.add(salvo);
            }
        }

        var response = PedidoMapper.toResponseDTO(pedidoSalvo, itensSalvos);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<PedidoResponse>> listarTodos() {
        var pedidos = pedidoService.listarTodosPedidos()
                .stream()
                .map(p -> {
                    var itens = itemPedidoService.buscarPorIdPedido(p.getId());
                    return PedidoMapper.toResponseDTO(p, itens);
                })
                .toList();
        return ResponseEntity.ok(pedidos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponse> buscarPorId(@PathVariable Long id) {
        Pedido pedido = pedidoService.buscarPorId(id);
        if (pedido == null) return ResponseEntity.notFound().build();

        var itens = itemPedidoService.buscarPorIdPedido(id);
        return ResponseEntity.ok(PedidoMapper.toResponseDTO(pedido, itens));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        boolean deletado = pedidoService.deletar(id);
        if (!deletado) return ResponseEntity.notFound().build();
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<PedidoResponse> atualizar(@PathVariable Long id, @RequestBody @Valid PedidoRequest request) {
        var atualizacao = new Pedido(null, request.getDate(), request.getStatus(), request.getIdCliente());
        var atualizado = pedidoService.atualizar(id, atualizacao);
        if (atualizado == null) return ResponseEntity.notFound().build();

        var itens = itemPedidoService.buscarPorIdPedido(id);
        return ResponseEntity.ok(PedidoMapper.toResponseDTO(atualizado, itens));
    }
}