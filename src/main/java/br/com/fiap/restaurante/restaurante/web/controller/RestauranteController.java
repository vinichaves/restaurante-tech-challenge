package br.com.fiap.restaurante.restaurante.web.controller;

import br.com.fiap.restaurante.restaurante.application.service.ClienteService;
import br.com.fiap.restaurante.restaurante.application.service.RestauranteService;
import br.com.fiap.restaurante.restaurante.domain.model.Cliente;
import br.com.fiap.restaurante.restaurante.domain.model.Restaurante;
import br.com.fiap.restaurante.restaurante.infrastructure.mapper.mapper.ClienteMapper;
import br.com.fiap.restaurante.restaurante.infrastructure.mapper.mapper.RestauranteMapper;
import br.com.fiap.restaurante.restaurante.web.dto.ClienteRequest;
import br.com.fiap.restaurante.restaurante.web.dto.ClienteResponse;
import br.com.fiap.restaurante.restaurante.web.dto.RestauranteRequest;
import br.com.fiap.restaurante.restaurante.web.dto.RestauranteResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurante")
public class RestauranteController {

    private final RestauranteService restauranteService;


    public RestauranteController(RestauranteService restauranteService) {
        this.restauranteService = restauranteService;
    }

    @PostMapping
    public ResponseEntity<RestauranteResponse> salvar(@RequestBody @Valid RestauranteRequest request) {
        var restaurante = new Restaurante(null, request.getNome(), request.getEndereco(), request.getTipoCozinha(), request.getHorarioFuncionamento(), request.getIdDono());
        var salvo = restauranteService.salvar(restaurante);
        return ResponseEntity.ok(RestauranteMapper.toResponseDTO(salvo));
    }

    @GetMapping
    public ResponseEntity<List<RestauranteResponse>> listarTodos() {
        var cliente = restauranteService.listarTodosRestaurantes()
                .stream()
                .map(RestauranteMapper::toResponseDTO)
                .toList();
        return ResponseEntity.ok(cliente);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Restaurante> buscarPorId(@PathVariable Long id) {
        Restaurante restaurante = restauranteService.buscarPorId(id);
        if (restaurante == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(restaurante);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        boolean deletado = restauranteService.deletar(id);
        if (!deletado) return ResponseEntity.notFound().build();
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Restaurante> atualizar(@PathVariable Long id, @RequestBody @Valid RestauranteRequest request) {
        var atualizacao = new Restaurante(null, request.getNome(), request.getEndereco(), request.getTipoCozinha(), request.getHorarioFuncionamento(), request.getIdDono());
        var atualizado = restauranteService.atualizar(id, atualizacao);
        if (atualizado == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(atualizado);
    }
}
