package br.com.teste.backend.service;

import br.com.teste.backend.dto.ProducerResponseDto;
import br.com.teste.backend.dto.ResponseDto;
import br.com.teste.backend.entity.Movie;
import br.com.teste.backend.repository.MovieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
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
        List<Movie> mockMovies = buildListMovie();
        when(movieRepository.findAllByWinnerTrue()).thenReturn(mockMovies);

        ResponseDto result = movieService.calculateIntervals();

        assertEquals(1, result.getMin().size());
        assertEquals(1, result.getMax().size());

        ProducerResponseDto minProducer = result.getMin().get(0);
        assertEquals("Producer 1", minProducer.getProducer());
        assertEquals(6, minProducer.getInterval());
        assertEquals(1995, minProducer.getPreviousWin());
        assertEquals(2001, minProducer.getFollowingWin());

        ProducerResponseDto maxProducer = result.getMax().get(0);
        assertEquals("Producer 1", maxProducer.getProducer());
        assertEquals(14, maxProducer.getInterval());
        assertEquals(2001, maxProducer.getPreviousWin());
        assertEquals(2015, maxProducer.getFollowingWin());
    }

    private List<Movie> buildListMovie() {
        List<Movie> mockMovies = new ArrayList<>();
        mockMovies.add(Movie.builder().yearMovie(1995).title("Movie X").studios("Studios A").producers("Producer 1").winner(true).build());
        mockMovies.add(Movie.builder().yearMovie(1998).title("Movie Y").studios("Studios B").producers("Producer 2").winner(false).build());
        mockMovies.add(Movie.builder().yearMovie(2001).title("Movie Z").studios("Studios C").producers("Producer 1").winner(true).build());
        mockMovies.add(Movie.builder().yearMovie(2003).title("Movie P").studios("Studios D").producers("Producer 3").winner(false).build());
        mockMovies.add(Movie.builder().yearMovie(2005).title("Movie Q").studios("Studios E").producers("Producer 2").winner(true).build());
        mockMovies.add(Movie.builder().yearMovie(2007).title("Movie R").studios("Studios F").producers("Producer 3").winner(false).build());
        mockMovies.add(Movie.builder().yearMovie(2009).title("Movie S").studios("Studios G").producers("Producer 4").winner(true).build());
        mockMovies.add(Movie.builder().yearMovie(2012).title("Movie T").studios("Studios H").producers("Producer 2").winner(false).build());
        mockMovies.add(Movie.builder().yearMovie(2015).title("Movie U").studios("Studios I").producers("Producer 1").winner(true).build());
        mockMovies.add(Movie.builder().yearMovie(2018).title("Movie V").studios("Studios J").producers("Producer 3").winner(false).build());
        return mockMovies;
    }

}
