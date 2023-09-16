package br.com.teste.backend.integration;

import br.com.teste.backend.dto.ResponseDto;
import br.com.teste.backend.service.MovieService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class MovieServiceIntegrationTest {

    @Autowired
    private MovieService movieService;

    /**
     * Testa o cálculo dos intervalos de prêmios usando o serviço MovieService.
     * Verifica se os intervalos mínimos e máximos esperados são encontrados no resultado.
     */
    @Test
    public void testCalculateIntervals() {
        // Calcula os intervalos de prêmios
        ResponseDto responseDto = movieService.calculateIntervals();

        // Verifica se o intervalo mínimo (1) e o intervalo máximo (9) estão presentes no resultado.
        boolean minIntervalFound = responseDto.getMin().stream()
                .anyMatch(producerResponseDto -> producerResponseDto.getInterval() == 1);

        boolean maxIntervalFound = responseDto.getMax().stream()
                .anyMatch(producerResponseDto -> producerResponseDto.getInterval() == 9);

        // Asserções para verificar se os intervalos foram encontrados no resultado.
        assertEquals(true, minIntervalFound);
        assertEquals(true, maxIntervalFound);
    }

}
