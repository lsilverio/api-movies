package br.com.teste.backend.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class MovieControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    /**
     * Testa o endpoint /movies/prizes para verificar se ele retorna o resultado esperado.
     * O teste verifica se o intervalo mínimo (1) e o intervalo máximo (9) estão presentes no JSON de resposta.
     */
    @Test
    public void testCalculateIntervals() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/movies/prizes")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.min[0].interval").value(1))
                .andExpect(jsonPath("$.max[0].interval").value(13));
    }

}
