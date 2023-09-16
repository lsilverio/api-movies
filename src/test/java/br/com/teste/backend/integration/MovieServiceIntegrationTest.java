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

    @Test
    public void testCalculateIntervals() {
        ResponseDto responseDto = movieService.calculateIntervals();

        boolean minIntervalFound = responseDto.getMin().stream()
                .anyMatch(producerResponseDto -> producerResponseDto.getInterval() == 1);

        boolean maxIntervalFound = responseDto.getMax().stream()
                .anyMatch(producerResponseDto -> producerResponseDto.getInterval() == 9);

        assertEquals(true, minIntervalFound);
        assertEquals(true, maxIntervalFound);
    }

}

