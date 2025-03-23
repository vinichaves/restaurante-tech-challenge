package br.com.fiap.restaurante.restaurante.application.service;

import br.com.fiap.restaurante.restaurante.domain.model.Usuario;
import br.com.fiap.restaurante.restaurante.infrastructure.persistence.UsuarioJpaRepository;
import br.com.fiap.restaurante.restaurante.infrastructure.mapper.mapper.UsuarioMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    private final UsuarioJpaRepository usuarioJpaRepository;

    public UsuarioService(UsuarioJpaRepository usuarioJpaRepository){
        this.usuarioJpaRepository = usuarioJpaRepository;
    }

    public Usuario salvar(Usuario usuario) {
        if (usuario.getId() != null) {
            throw new IllegalArgumentException("Não envie ID ao criar um novo usuário.");
        }
        var entity = UsuarioMapper.toEntity(usuario);
        var saved = usuarioJpaRepository.save(entity);
        return UsuarioMapper.toDomain(saved);
    }

    public List<Usuario> listarTodosUsuarios(){
        return usuarioJpaRepository.findAll()
                .stream()
                .map(UsuarioMapper::toDomain)
                .collect(Collectors.toList());
    }

    public Usuario buscarPorId(Long id){
        return usuarioJpaRepository.findById(id)
                .map(UsuarioMapper::toDomain)
                .orElse(null);
    }

    public boolean deletar(Long id) {
        if (!usuarioJpaRepository.existsById(id)) return false;
        usuarioJpaRepository.deleteById(id);
        return true;
    }

    public Usuario atualizar(Long id, Usuario dadosAtualizados) {
        var existenteOpt = usuarioJpaRepository.findById(id);
        if (existenteOpt.isEmpty()) return null;

        if (dadosAtualizados.getNome() == null &&
                dadosAtualizados.getEmail() == null &&
                dadosAtualizados.getSenha() == null) {
            throw new IllegalArgumentException("Nenhum dado para atualizar.");
        }

        var existente = existenteOpt.get();

        if (dadosAtualizados.getNome() != null) {
            existente.setNome(dadosAtualizados.getNome());
        }
        if (dadosAtualizados.getEmail() != null) {
            existente.setEmail(dadosAtualizados.getEmail());
        }
        if (dadosAtualizados.getSenha() != null) {
            existente.setSenha(dadosAtualizados.getSenha());
        }

        var atualizado = usuarioJpaRepository.save(existente);
        return UsuarioMapper.toDomain(atualizado);
    }


}
