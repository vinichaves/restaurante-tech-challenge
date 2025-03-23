package br.com.fiap.restaurante.restaurante.web.controller;

import br.com.fiap.restaurante.restaurante.application.service.ClienteService;
import br.com.fiap.restaurante.restaurante.application.service.UsuarioService;
import br.com.fiap.restaurante.restaurante.domain.model.Cliente;
import br.com.fiap.restaurante.restaurante.domain.model.Usuario;
import br.com.fiap.restaurante.restaurante.infrastructure.mapper.mapper.ClienteMapper;
import br.com.fiap.restaurante.restaurante.infrastructure.mapper.mapper.UsuarioMapper;
import br.com.fiap.restaurante.restaurante.web.dto.ClienteRequest;
import br.com.fiap.restaurante.restaurante.web.dto.ClienteResponse;
import br.com.fiap.restaurante.restaurante.web.dto.UsuarioRequest;
import br.com.fiap.restaurante.restaurante.web.dto.UsuarioResponseDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cliente")
public class ClienteController {

    private final ClienteService clienteService;


    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @PostMapping
    public ResponseEntity<ClienteResponse> salvar(@RequestBody @Valid ClienteRequest request) {
        var cliente = new Cliente(null, request.getNome(), request.getEmail(), request.getCpf(), request.getTelefone());
        var salvo = clienteService.salvar(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(ClienteMapper.toResponseDTO(salvo));
    }

    @GetMapping
    public ResponseEntity<List<ClienteResponse>> listarTodos() {
        var cliente = clienteService.listarTodosClientes()
                .stream()
                .map(ClienteMapper::toResponseDTO)
                .toList();
        return ResponseEntity.ok(cliente);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> buscarPorId(@PathVariable Long id) {
        Cliente cliente = clienteService.buscarPorId(id);
        if (cliente == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(cliente);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        boolean deletado = clienteService.deletar(id);
        if (!deletado) return ResponseEntity.notFound().build();
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cliente> atualizar(@PathVariable Long id, @RequestBody @Valid ClienteRequest request) {
        var atualizacao = new Cliente(null, request.getNome(), request.getEmail(), request.getCpf(), request.getTelefone());
        var atualizado = clienteService.atualizar(id, atualizacao);
        if (atualizado == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(atualizado);
    }
}
