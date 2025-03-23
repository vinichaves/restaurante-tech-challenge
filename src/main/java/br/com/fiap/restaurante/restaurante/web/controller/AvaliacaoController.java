package br.com.fiap.restaurante.restaurante.web.controller;


import br.com.fiap.restaurante.restaurante.application.service.AvaliacaoService;
import br.com.fiap.restaurante.restaurante.application.service.ClienteService;
import br.com.fiap.restaurante.restaurante.domain.model.Avaliacao;
import br.com.fiap.restaurante.restaurante.domain.model.Cliente;
import br.com.fiap.restaurante.restaurante.infrastructure.mapper.mapper.AvaliacaoMapper;
import br.com.fiap.restaurante.restaurante.infrastructure.mapper.mapper.ClienteMapper;
import br.com.fiap.restaurante.restaurante.web.dto.AvaliacaoRequest;
import br.com.fiap.restaurante.restaurante.web.dto.AvaliacaoResponse;
import br.com.fiap.restaurante.restaurante.web.dto.ClienteRequest;
import br.com.fiap.restaurante.restaurante.web.dto.ClienteResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/avaliacao")
public class AvaliacaoController {

    private final AvaliacaoService avaliacaoService;


    public AvaliacaoController(AvaliacaoService avaliacaoService) {
        this.avaliacaoService = avaliacaoService;
    }

    @PostMapping
    public ResponseEntity<AvaliacaoResponse> salvar(@RequestBody @Valid AvaliacaoRequest request) {
        var avaliacao = new Avaliacao(null, request.getNota(), request.getComentario(), request.getData(), request.getIdCliente(),request.getIdRestaurante());
        var salvo = avaliacaoService.salvar(avaliacao);
        return ResponseEntity.ok(AvaliacaoMapper.toResponseDTO(salvo));
    }

    @GetMapping
    public ResponseEntity<List<AvaliacaoResponse>> listarTodasAsAvaliacoes() {
        var avaliacoes = avaliacaoService.listarTodasAvaliacoes()
                .stream()
                .map(AvaliacaoMapper::toResponseDTO)
                .toList();
        return ResponseEntity.ok(avaliacoes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AvaliacaoResponse> buscarPorId(@PathVariable Long id) {
        Avaliacao avaliacao = avaliacaoService.buscarPorId(id);
        if (avaliacao == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(AvaliacaoMapper.toResponseDTO(avaliacao));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        boolean deletado = avaliacaoService.deletar(id);
        if (!deletado) return ResponseEntity.notFound().build();
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<AvaliacaoResponse> atualizar(@PathVariable Long id, @RequestBody @Valid AvaliacaoRequest request) {
        var atualizacao = new Avaliacao(null, request.getNota(), request.getComentario(), request.getData(), request.getIdCliente(), request.getIdRestaurante());
        var atualizado = avaliacaoService.atualizar(id, atualizacao);
        if (atualizado == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(AvaliacaoMapper.toResponseDTO(atualizado));
    }
}
