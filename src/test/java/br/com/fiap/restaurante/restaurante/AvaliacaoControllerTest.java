package br.com.fiap.restaurante.restaurante;

import br.com.fiap.restaurante.restaurante.application.service.AvaliacaoService;
import br.com.fiap.restaurante.restaurante.config.RecursoNaoEncontradoException;
import br.com.fiap.restaurante.restaurante.domain.model.Avaliacao;
import br.com.fiap.restaurante.restaurante.web.controller.AvaliacaoController;
import br.com.fiap.restaurante.restaurante.web.dto.AvaliacaoRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AvaliacaoController.class)
public class AvaliacaoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AvaliacaoService avaliacaoService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void deveCriarAvaliacao() throws Exception {
        AvaliacaoRequest request = new AvaliacaoRequest(5, "Muito bom", LocalDateTime.now(),1L, 2L);
        Avaliacao saved = new Avaliacao(1L, request.getNota(), request.getComentario(), request.getData(), request.getIdCliente(), request.getIdRestaurante());

        given(avaliacaoService.salvar(any())).willReturn(saved);

        mockMvc.perform(post("/avaliacao")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nota").value(5))
                .andExpect(jsonPath("$.comentario").value("Muito bom"));
    }

    @Test
    void deveBuscarAvaliacaoPorId() throws Exception {
        Avaliacao avaliacao = new Avaliacao(1L, 4, "Bom", LocalDateTime.now(), 1L, 2L);
        given(avaliacaoService.buscarPorId(1L)).willReturn(avaliacao);

        mockMvc.perform(get("/avaliacao/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nota").value(4))
                .andExpect(jsonPath("$.comentario").value("Bom"));
    }

    @Test
    void deveListarTodasAsAvaliacoes() throws Exception {
        var lista = List.of(
                new Avaliacao(1L, 5, "Top", LocalDateTime.now(), 1L, 2L),
                new Avaliacao(2L, 3, "Mais ou menos", LocalDateTime.now(), 1L, 2L)
        );
        given(avaliacaoService.listarTodasAvaliacoes()).willReturn(lista);

        mockMvc.perform(get("/avaliacao"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(2)))
                .andExpect(jsonPath("$[0].comentario").value("Top"))
                .andExpect(jsonPath("$[1].comentario").value("Mais ou menos"));
    }

    @Test
    void deveAtualizarAvaliacao() throws Exception {
        AvaliacaoRequest request = new AvaliacaoRequest(2, "Ruim", LocalDateTime.now(), 1L, 2L);
        Avaliacao atualizado = new Avaliacao(1L, request.getNota(), request.getComentario(), request.getData(), request.getIdCliente(), request.getIdRestaurante());

        given(avaliacaoService.atualizar(Mockito.eq(1L), any())).willReturn(atualizado);

        mockMvc.perform(put("/avaliacao/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.comentario").value("Ruim"));
    }

    @Test
    void deveDeletarAvaliacao() throws Exception {
        given(avaliacaoService.deletar(1L)).willReturn(true);

        mockMvc.perform(delete("/avaliacao/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void deveRetornar404_QuandoAvaliacaoNaoExistir() throws Exception {
        given(avaliacaoService.buscarPorId(999L))
                .willThrow(new RecursoNaoEncontradoException("Avaliação não encontrada"));

        mockMvc.perform(get("/avaliacao/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void deveRetornar400_QuandoRequestInvalida() throws Exception {
        AvaliacaoRequest requestInvalido = new AvaliacaoRequest(null, "", null, null, null);

        mockMvc.perform(post("/avaliacao")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestInvalido)))
                .andExpect(status().isBadRequest());
    }
}