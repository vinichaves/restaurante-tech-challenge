package br.com.fiap.restaurante.restaurante.web.controller;

import br.com.fiap.restaurante.restaurante.application.service.ItemCardapioService;
import br.com.fiap.restaurante.restaurante.domain.model.ItemCardapio;
import br.com.fiap.restaurante.restaurante.infrastructure.mapper.mapper.ItemCardapioMapper;
import br.com.fiap.restaurante.restaurante.web.dto.ItemCardapioRequest;
import br.com.fiap.restaurante.restaurante.web.dto.ItemCardapioResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/item-cardapio")
public class ItemCardapioController {

    private final ItemCardapioService service;

    public ItemCardapioController(ItemCardapioService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ItemCardapioResponse> salvar(@RequestBody @Valid ItemCardapioRequest request) {
        var item = ItemCardapioMapper.toDomain(request);
        var salvo = service.salvar(item);
        return ResponseEntity.ok(ItemCardapioMapper.toResponseDTO(salvo));
    }

    @GetMapping
    public ResponseEntity<List<ItemCardapioResponse>> listarTodos() {
        var lista = service.listarTodos()
                .stream()
                .map(ItemCardapioMapper::toResponseDTO)
                .toList();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemCardapioResponse> buscarPorId(@PathVariable Long id) {
        var item = service.buscarPorId(id);
        if (item == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(ItemCardapioMapper.toResponseDTO(item));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ItemCardapioResponse> atualizar(@PathVariable Long id, @RequestBody @Valid ItemCardapioRequest request) {
        var atualizado = service.atualizar(id, ItemCardapioMapper.toDomain(request));
        if (atualizado == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(ItemCardapioMapper.toResponseDTO(atualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        boolean deletado = service.deletar(id);
        if (!deletado) return ResponseEntity.notFound().build();
        return ResponseEntity.noContent().build();
    }
}