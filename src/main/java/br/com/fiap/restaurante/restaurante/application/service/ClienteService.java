package br.com.fiap.restaurante.restaurante.application.service;


import br.com.fiap.restaurante.restaurante.domain.model.Cliente;
import br.com.fiap.restaurante.restaurante.domain.model.Usuario;
import br.com.fiap.restaurante.restaurante.infrastructure.mapper.mapper.ClienteMapper;
import br.com.fiap.restaurante.restaurante.infrastructure.mapper.mapper.UsuarioMapper;
import br.com.fiap.restaurante.restaurante.infrastructure.persistence.ClienteRepository;
import br.com.fiap.restaurante.restaurante.infrastructure.persistence.TipoUsuarioRepository;
import br.com.fiap.restaurante.restaurante.infrastructure.persistence.UsuarioJpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClienteService {

    private final ClienteRepository repository;

    public ClienteService(ClienteRepository repository) {
        this.repository = repository;
    }

    public Cliente salvar(Cliente cliente) {
        if (cliente.getId() != null) {
            throw new IllegalArgumentException("Não envie ID ao criar um novo usuário.");
        }
        var entity = ClienteMapper.toEntity(cliente);
        var saved = repository.save(entity);
        return ClienteMapper.toDomain(saved);
    }

    public List<Cliente> listarTodosClientes() {
        return repository.findAll()
                .stream()
                .map(ClienteMapper::toDomain)
                .collect(Collectors.toList());
    }

    public Cliente buscarPorId(Long id){
        return repository.findById(id)
                .map(ClienteMapper::toDomain)
                .orElse(null);
    }

    public boolean deletar(Long id) {
        if (!repository.existsById(id)) return false;
        repository.deleteById(id);
        return true;
    }

    public Cliente atualizar(Long id, Cliente dadosAtualizados) {
        var existenteOpt = repository.findById(id);
        if (existenteOpt.isEmpty()) return null;

        if (
                dadosAtualizados.getNome() == null &&
                dadosAtualizados.getEmail() == null &&
                dadosAtualizados.getCpf() == null &&
                dadosAtualizados.getTelefone() == null) {
            throw new IllegalArgumentException("Nenhum dado para atualizar.");
        }

        var existente = existenteOpt.get();

        if (dadosAtualizados.getNome() != null) {
            existente.setNome(dadosAtualizados.getNome());
        }
        if (dadosAtualizados.getEmail() != null) {
            existente.setEmail(dadosAtualizados.getEmail());
        }
        if (dadosAtualizados.getCpf() != null) {
            existente.setCpf(dadosAtualizados.getCpf());
        }
        if (dadosAtualizados.getTelefone() != null) {
            existente.setTelefone(dadosAtualizados.getTelefone());
        }

        var atualizado = repository.save(existente);
        return ClienteMapper.toDomain(atualizado);
    }
}
