package br.com.fiap.restaurante.restaurante;

import br.com.fiap.restaurante.restaurante.domain.Enum.StatusPedido;
import br.com.fiap.restaurante.restaurante.domain.model.Cliente;
import br.com.fiap.restaurante.restaurante.domain.model.Pedido;
import br.com.fiap.restaurante.restaurante.infrastructure.persistence.ClienteRepository;
import br.com.fiap.restaurante.restaurante.infrastructure.persistence.ItemPedidoRepository;
import br.com.fiap.restaurante.restaurante.infrastructure.persistence.PedidoRepository;
import br.com.fiap.restaurante.restaurante.web.dto.PedidoRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.test.annotation.Rollback;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Rollback
class PedidoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    private Long clienteId;
    private Long pedidoId;


    @BeforeEach
    void setup() throws Exception {
        String emailUnico = "cliente" + UUID.randomUUID() + "@teste.com";

        String clienteJson = """
            {
              "nome": "Cliente Teste",
              "email": "%s",
              "cpf": "12345678901",
              "telefone": "11999999999"
            }
        """.formatted(emailUnico);

        var result = mockMvc.perform(post("/cliente")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(clienteJson))
                .andExpect(status().isCreated())
                .andReturn();

        String responseJson = result.getResponse().getContentAsString();
        clienteId = objectMapper.readTree(responseJson).get("id").asLong();


        String pedidoJson = """
        {
          "idCliente": %d,
          "status": "%s",
          "itens": [
            {
              "descricao": "Hamburguer",
              "quantidade": 2,
              "precoUnitario": 25.5
            },
            {
              "descricao": "Refrigerante",
              "quantidade": 1,
              "precoUnitario": 7.0
            }
          ]
        }
        """.formatted(clienteId, StatusPedido.RECEBIDO);

        var pedidoResult = mockMvc.perform(post("/pedido")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(pedidoJson))
                .andExpect(status().isCreated())
                .andReturn();

        // Extrair o ID do pedido criado
        String pedidoResponseJson = pedidoResult.getResponse().getContentAsString();
        pedidoId = objectMapper.readTree(pedidoResponseJson).get("id").asLong();
    }

    @Test
    @DisplayName("Deve listar todos os pedidos")
    void deveListarTodosPedidos() throws Exception {
        mockMvc.perform(get("/pedido"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(pedidoId));
    }

    @Test
    @DisplayName("Deve buscar um pedido por ID")
    void deveBuscarPedidoPorId() throws Exception {
        mockMvc.perform(get("/pedido/{id}", pedidoId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(pedidoId))
                .andExpect(jsonPath("$.idCliente").value(clienteId));
    }

    @Test
    @DisplayName("Deve retornar 404 quando o pedido não for encontrado")
    void deveRetornar404QuandoPedidoNaoEncontrado() throws Exception {
        mockMvc.perform(get("/pedido/{id}", 99999L))
                .andExpect(status().isNotFound());
    }
//
//    @Test
//    @DisplayName("Deve atualizar um pedido")
//    void deveAtualizarPedido() throws Exception {
//        // 1. Criar o pedido
//        MvcResult createResult = mockMvc.perform(post("/pedido")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"idCliente\": " + clienteId + ", \"status\": \"RECEBIDO\", \"itens\": [ {\"descricao\": \"Hamburguer\", \"quantidade\": 2, \"precoUnitario\": 25.5}, {\"descricao\": \"Refrigerante\", \"quantidade\": 1, \"precoUnitario\": 7.0} ]}"))
//                .andExpect(status().isCreated())  // Espera-se 201 (Created)
//                .andExpect(jsonPath("$.id").exists())  // Verifica se o ID está presente
//                .andReturn();
//
//        // 2. Obter o ID do pedido da resposta
//        String responseContent = createResult.getResponse().getContentAsString();
//        Integer pedidoId = JsonPath.read(responseContent, "$.id");  // Usando JsonPath para pegar o ID do pedido criado
//
//        // 3. Atualizar o pedido com o ID obtido, mas **modificar o item existente**, não adicionar novo item
//        mockMvc.perform(put("/pedido/{id}", pedidoId)  // Usar o ID obtido dinamicamente
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"idCliente\": " + clienteId + ", \"status\": \"FINALIZADO\", \"itens\": [ {\"id\": 137, \"descricao\": \"Pizza\", \"quantidade\": 1, \"precoUnitario\": 30.0} ]}"))
//                .andExpect(status().isOk())  // Espera-se 200 OK
//                .andExpect(jsonPath("$.id").value(pedidoId))  // Verifica se o ID correto é retornado
//                .andExpect(jsonPath("$.status").value("FINALIZADO"))
//                .andExpect(jsonPath("$.idCliente").value(clienteId))  // Verifica se o ID do cliente está correto no pedido
//                .andExpect(jsonPath("$.itens.length()").value(1))  // Agora esperamos apenas 1 item, não 2
//                .andExpect(jsonPath("$.itens[0].descricao").value("Pizza"))
//                .andExpect(jsonPath("$.itens[0].quantidade").value(1))
//                .andExpect(jsonPath("$.itens[0].precoUnitario").value(30.0));  // Verifica o preço unitário
//    }

    @Test
    @DisplayName("Deve retornar 404 ao tentar atualizar pedido inexistente")
    void deveRetornar404AoAtualizarPedidoInexistente() throws Exception {
        String pedidoAtualizadoJson = """
        {
          "idCliente": %d,
          "status": "%s",
          "itens": [
            {
              "descricao": "Pizza",
              "quantidade": 1,
              "precoUnitario": 30.0
            }
          ]
        }
        """.formatted(clienteId, StatusPedido.FINALIZADO);

        mockMvc.perform(put("/pedido/{id}", 99999L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(pedidoAtualizadoJson))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Deve deletar um pedido")
    void deveDeletarPedido() throws Exception {
        mockMvc.perform(delete("/pedido/{id}", pedidoId))
                .andExpect(status().isNoContent());

        // Verificar se o pedido foi realmente deletado
        mockMvc.perform(get("/pedido/{id}", pedidoId))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Deve retornar 404 ao tentar deletar pedido inexistente")
    void deveRetornar404AoDeletarPedidoInexistente() throws Exception {
        mockMvc.perform(delete("/pedido/{id}", 99999L))
                .andExpect(status().isNotFound());
    }
}