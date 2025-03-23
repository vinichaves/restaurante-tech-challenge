package br.com.fiap.restaurante.restaurante.application.service;

import br.com.fiap.restaurante.restaurante.infrastructure.persistence.TipoUsuarioRepository;
import br.com.fiap.restaurante.restaurante.infrastructure.mapper.mapper.TipoUsuarioMapper;
import br.com.fiap.restaurante.restaurante.web.dto.TipoUsuarioRequest;
import br.com.fiap.restaurante.restaurante.web.dto.TipoUsuarioResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TipoUsuarioService {

    private final TipoUsuarioRepository repository;

    public TipoUsuarioService(TipoUsuarioRepository repository) {
        this.repository = repository;
    }

    public TipoUsuarioResponse salvar(TipoUsuarioRequest request) {
        var entity = TipoUsuarioMapper.toEntity(request);
        var salvo = repository.save(entity);
        return TipoUsuarioMapper.toResponse(salvo);
    }

    public List<TipoUsuarioResponse> listarTodos() {
        return repository.findAll().stream()
                .map(TipoUsuarioMapper::toResponse)
                .toList();
    }

    public TipoUsuarioResponse buscarPorId(Long id) {
        return repository.findById(id)
                .map(TipoUsuarioMapper::toResponse)
                .orElse(null);
    }

    public boolean deletar(Long id) {
        if (!repository.existsById(id)) return false;
        repository.deleteById(id);
        return true;
    }
}
