package br.com.fiap.restaurante.restaurante.web.controller;


import br.com.fiap.restaurante.restaurante.application.service.UsuarioService;
import br.com.fiap.restaurante.restaurante.domain.model.Usuario;
import br.com.fiap.restaurante.restaurante.infrastructure.mapper.mapper.UsuarioMapper;
import br.com.fiap.restaurante.restaurante.web.dto.UsuarioRequest;
import br.com.fiap.restaurante.restaurante.web.dto.UsuarioResponseDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;


    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> salvar(@RequestBody Usuario usuario) {
        Usuario novo = usuarioService.salvar(usuario);
        return ResponseEntity.ok(UsuarioMapper.toResponseDTO(novo));
    }

    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> listarTodos() {
        var usuarios = usuarioService.listarTodosUsuarios()
                .stream()
                .map(UsuarioMapper::toResponseDTO)
                .toList();
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarPorId(@PathVariable Long id) {
        Usuario usuario = usuarioService.buscarPorId(id);
        if (usuario == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(usuario);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        boolean deletado = usuarioService.deletar(id);
        if (!deletado) return ResponseEntity.notFound().build();
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> atualizar(@PathVariable Long id, @RequestBody @Valid UsuarioRequest request) {
        var atualizacao = new Usuario(null, request.getNome(), request.getEmail(), request.getSenha());
        var atualizado = usuarioService.atualizar(id, atualizacao);
        if (atualizado == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(atualizado);
    }
}
