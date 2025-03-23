package br.com.fiap.restaurante.restaurante.web.controller;

import br.com.fiap.restaurante.restaurante.application.service.TipoUsuarioService;
import br.com.fiap.restaurante.restaurante.web.dto.TipoUsuarioRequest;
import br.com.fiap.restaurante.restaurante.web.dto.TipoUsuarioResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tipos-usuarios")
public class TipoUsuarioController {

    private final TipoUsuarioService service;

    public TipoUsuarioController(TipoUsuarioService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<TipoUsuarioResponse> salvar(@RequestBody @Valid TipoUsuarioRequest request) {
        return ResponseEntity.ok(service.salvar(request));
    }

    @GetMapping
    public ResponseEntity<List<TipoUsuarioResponse>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoUsuarioResponse> buscarPorId(@PathVariable Long id) {
        var tipo = service.buscarPorId(id);
        return tipo != null ? ResponseEntity.ok(tipo) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        return service.deletar(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}