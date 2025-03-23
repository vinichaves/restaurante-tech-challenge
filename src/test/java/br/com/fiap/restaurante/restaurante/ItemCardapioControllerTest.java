package br.com.fiap.restaurante.restaurante;

import br.com.fiap.restaurante.restaurante.infrastructure.persistence.*;
import br.com.fiap.restaurante.restaurante.web.dto.ItemCardapioRequest;
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
public class ItemCardapioControllerTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    @Autowired private ItemCardapioRepository itemCardapioRepository;
    @Autowired private RestauranteRepository restauranteRepository;
    @Autowired private UsuarioJpaRepository usuarioRepository;

    private Long restauranteId;

    @BeforeEach
    public void setup() {
        itemCardapioRepository.deleteAll();
        restauranteRepository.deleteAll();
        usuarioRepository.deleteAll();

        var dono = usuarioRepository.save(UsuarioEntity.builder()
                .nome("Dono Teste")
                .email("dono@email.com")
                .senha("123456")
                .build());

        var restaurante = restauranteRepository.save(RestauranteEntity.builder()
                .nome("Restaurante Teste")
                .endereco("Rua Teste, 123")
                .horarioFuncionamento("10h às 22h")
                .tipoCozinha("Italiana")
                .dono(dono)
                .build());

        restauranteId = restaurante.getId();
    }

    @Test
    @Order(1)
    public void deveCriarItemCardapio() throws Exception {
        var request = new ItemCardapioRequest("Pizza", "Deliciosa", 35.0, restauranteId, false, "caminho/foto.jpg");

        mockMvc.perform(post("/item-cardapio")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Pizza"))
                .andExpect(jsonPath("$.preco").value(35.0));
    }

    @Test
    @Order(2)
    public void deveListarTodosOsItensCardapio() throws Exception {
        mockMvc.perform(get("/item-cardapio"))
                .andExpect(status().isOk());
    }

    @Test
    @Order(3)
    public void deveBuscarItemCardapioPorId() throws Exception {
        var request = new ItemCardapioRequest("Coca-Cola", "Refrigerante", 7.5, restauranteId, true, "caminho/img.jpg");

        var response = mockMvc.perform(post("/item-cardapio")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andReturn();

        Long itemId = objectMapper.readTree(response.getResponse().getContentAsString()).get("id").asLong();

        mockMvc.perform(get("/item-cardapio/" + itemId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Coca-Cola"));
    }

    @Test
    @Order(4)
    public void deveAtualizarItemCardapio() throws Exception {
        var request = new ItemCardapioRequest("Hamburguer", "Tradicional", 20.0, restauranteId, false, null);

        var response = mockMvc.perform(post("/item-cardapio")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andReturn();

        Long itemId = objectMapper.readTree(response.getResponse().getContentAsString()).get("id").asLong();

        var updateRequest = new ItemCardapioRequest("Hamburguer Duplo", "Com bacon", 25.0, restauranteId, true, "foto.jpg");

        mockMvc.perform(put("/item-cardapio/" + itemId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Hamburguer Duplo"));
    }

    @Test
    @Order(5)
    public void deveDeletarItemCardapio() throws Exception {
        var request = new ItemCardapioRequest("Batata Frita", "Com cheddar", 12.0, restauranteId, false, null);

        var response = mockMvc.perform(post("/item-cardapio")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andReturn();

        Long itemId = objectMapper.readTree(response.getResponse().getContentAsString()).get("id").asLong();

        mockMvc.perform(delete("/item-cardapio/" + itemId))
                .andExpect(status().isNoContent());
    }

    @Test
    @Order(6)
    public void deveRetornar404_QuandoItemNaoExiste() throws Exception {
        mockMvc.perform(get("/item-cardapio/999999"))
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(7)
    public void deveRetornar400_QuandoRequestInvalida() throws Exception {
        var request = new ItemCardapioRequest("", "", -5.0, null, false, "");

        mockMvc.perform(post("/item-cardapio")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
}