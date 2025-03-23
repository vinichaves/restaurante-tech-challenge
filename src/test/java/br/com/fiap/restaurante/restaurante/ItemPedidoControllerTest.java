package br.com.fiap.restaurante.restaurante;

import br.com.fiap.restaurante.restaurante.infrastructure.persistence.*;
import br.com.fiap.restaurante.restaurante.web.dto.ItemPedidoRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ItemPedidoControllerTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    @Autowired private PedidoRepository pedidoRepository;
    @Autowired private ClienteRepository clienteRepository;

    private Long pedidoId;

    @BeforeEach
    public void setup() {
        pedidoRepository.deleteAll();
        clienteRepository.deleteAll();

        var cliente = clienteRepository.save(
                ClienteEntity.builder()
                        .nome("Cliente Teste")
                        .email("cliente@teste.com")
                        .cpf("12345678900")
                        .telefone("11999999999")
                        .build()
        );

        var pedido = pedidoRepository.save(
                PedidoEntity.builder()
                        .date(java.time.LocalDateTime.now())
                        .status(br.com.fiap.restaurante.restaurante.domain.Enum.StatusPedido.RECEBIDO)
                        .cliente(cliente)
                        .build()
        );

        pedidoId = pedido.getId();
    }

    @Test
    @Order(1)
    public void deveCriarItemPedido() throws Exception {
        var request = new ItemPedidoRequest("Pizza", 2L, pedidoId);

        mockMvc.perform(post("/item-pedido")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.produto").value("Pizza"))
                .andExpect(jsonPath("$.quantidade").value(2));
    }

    @Test
    @Order(2)
    public void deveListarTodosOsItensPedido() throws Exception {
        mockMvc.perform(get("/item-pedido"))
                .andExpect(status().isOk());
    }

    @Test
    @Order(3)
    public void deveBuscarItemPedidoPorId() throws Exception {
        var request = new ItemPedidoRequest("Coca-Cola", 1L, pedidoId);

        var response = mockMvc.perform(post("/item-pedido")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andReturn();

        Long itemId = objectMapper.readTree(response.getResponse().getContentAsString()).get("id").asLong();

        mockMvc.perform(get("/item-pedido/" + itemId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.produto").value("Coca-Cola"));
    }

    @Test
    @Order(4)
    public void deveAtualizarItemPedido() throws Exception {
        var request = new ItemPedidoRequest("Hamburguer", 1L, pedidoId);

        var response = mockMvc.perform(post("/item-pedido")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andReturn();

        Long itemId = objectMapper.readTree(response.getResponse().getContentAsString()).get("id").asLong();

        var update = new ItemPedidoRequest("Hamburguer Duplo", 2L, pedidoId);

        mockMvc.perform(put("/item-pedido/" + itemId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(update)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.produto").value("Hamburguer Duplo"))
                .andExpect(jsonPath("$.quantidade").value(2));
    }

    @Test
    @Order(5)
    public void deveDeletarItemPedido() throws Exception {
        var request = new ItemPedidoRequest("Batata", 1L, pedidoId);

        var response = mockMvc.perform(post("/item-pedido")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andReturn();

        Long itemId = objectMapper.readTree(response.getResponse().getContentAsString()).get("id").asLong();

        mockMvc.perform(delete("/item-pedido/" + itemId))
                .andExpect(status().isNoContent());
    }

    @Test
    @Order(6)
    public void deveRetornar404_QuandoItemNaoExiste() throws Exception {
        mockMvc.perform(get("/item-pedido/99999"))
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(7)
    public void deveRetornar400_QuandoRequestInvalida() throws Exception {
        var request = new ItemPedidoRequest("", null, null);

        mockMvc.perform(post("/item-pedido")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
}