package br.com.teste.backend.service;

import br.com.teste.backend.dto.ResponseDto;
import br.com.teste.backend.entity.Movie;
import br.com.teste.backend.repository.MovieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class MovieServiceTest {

    @Autowired
    private MovieService movieService;

    @MockBean
    private MovieRepository movieRepository;

    @BeforeEach
    public void setup() {
        movieService = new MovieService(movieRepository);
    }

    @Test
    public void testCalculateIntervals() {

        when(movieRepository.findAllByWinnerTrue()).thenReturn(buildListMovie());
        ResponseDto result = movieService.calculateIntervals();

        assertEquals(2, result.getMin().size());
        assertEquals(2, result.getMax().size());

        result.getMin().forEach(minProducer -> {
            assertEquals(1, minProducer.getInterval());
        });

        result.getMax().forEach(maxProducer -> {
            assertEquals(15, maxProducer.getInterval());
        });
    }

    private List<Movie> buildListMovie() {
        List<Movie> mockMovies = new ArrayList<>();
        mockMovies.add(Movie.builder().yearMovie(1995).title("Movie X").studios("Studios A").producers("Producer 1").winner(true).build());
        mockMovies.add(Movie.builder().yearMovie(2000).title("Movie Z").studios("Studios C").producers("Producer 1").winner(true).build());
        mockMovies.add(Movie.builder().yearMovie(2015).title("Movie U").studios("Studios I").producers("Producer 1").winner(true).build());
        mockMovies.add(Movie.builder().yearMovie(1998).title("Movie Y").studios("Studios B").producers("Producer 2").winner(false).build());
        mockMovies.add(Movie.builder().yearMovie(2005).title("Movie Q").studios("Studios E").producers("Producer 2").winner(true).build());
        mockMovies.add(Movie.builder().yearMovie(2012).title("Movie T").studios("Studios H").producers("Producer 2").winner(false).build());
        mockMovies.add(Movie.builder().yearMovie(2003).title("Movie P").studios("Studios D").producers("Producer 3").winner(true).build());
        mockMovies.add(Movie.builder().yearMovie(2004).title("Movie R").studios("Studios F").producers("Producer 3").winner(true).build());
        mockMovies.add(Movie.builder().yearMovie(2018).title("Movie V").studios("Studios J").producers("Producer 3").winner(false).build());
        mockMovies.add(Movie.builder().yearMovie(2000).title("Movie S").studios("Studios G").producers("Producer 4").winner(true).build());
        mockMovies.add(Movie.builder().yearMovie(2015).title("Movie S").studios("Studios G").producers("Producer 4").winner(true).build());
        mockMovies.add(Movie.builder().yearMovie(2014).title("Movie S").studios("Studios G").producers("Producer 5").winner(true).build());
        mockMovies.add(Movie.builder().yearMovie(2015).title("Movie S").studios("Studios G").producers("Producer 5").winner(true).build());
        return mockMovies;
    }

}
