package br.com.fiap.restaurante.restaurante;

import br.com.fiap.restaurante.restaurante.web.dto.ClienteRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ClienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private static Long idCriado;

    private String gerarEmailDinamico() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString() + "@dominio.com";
    }

    @Test
    @Order(1)
    void deveCriarClienteComSucesso() throws Exception {
        var request = new ClienteRequest();
        request.setNome("João da Silva");
        request.setEmail(gerarEmailDinamico());
        request.setCpf("12345678900");
        request.setTelefone("11999999999");

        var result = mockMvc.perform(post("/cliente")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andReturn();

        // Extrair ID para usar nos próximos testes
        String response = result.getResponse().getContentAsString();
        idCriado = objectMapper.readTree(response).get("id").asLong();
    }

    @Test
    @Order(2)
    void deveBuscarClientePorId() throws Exception {
        mockMvc.perform(get("/cliente/" + idCriado))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(idCriado));
    }

    @Test
    @Order(3)
    void deveAtualizarCliente() throws Exception {
        var request = new ClienteRequest();
        request.setNome("João Atualizado");
        request.setEmail(gerarEmailDinamico());
        request.setCpf("12345678900");
        request.setTelefone("11988887777");

        mockMvc.perform(put("/cliente/" + idCriado)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("João Atualizado"));
    }

    @Test
    @Order(4)
    void deveListarTodosClientes() throws Exception {
        mockMvc.perform(get("/cliente"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThan(0))));
    }

    @Test
    @Order(5)
    void deveDeletarCliente() throws Exception {
        mockMvc.perform(delete("/cliente/" + idCriado))
                .andExpect(status().isNoContent());
    }

    @Test
    @Order(6)
    void deveRetornarNotFoundAoBuscarClienteExcluido() throws Exception {
        mockMvc.perform(get("/cliente/" + idCriado))
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(7)
    void deveRetornar400_QuandoRequestInvalida() throws Exception {
        var request = new ClienteRequest();
        request.setNome("");
        request.setEmail("email_invalido");
        request.setCpf(null);
        request.setTelefone("");

        mockMvc.perform(post("/cliente")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
}