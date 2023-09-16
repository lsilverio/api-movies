package br.com.teste.backend.service;

import br.com.teste.backend.dto.MovieWinnerResponseDto;
import br.com.teste.backend.dto.ProducerResponseDto;
import br.com.teste.backend.dto.ResponseDto;
import br.com.teste.backend.entity.Movie;
import br.com.teste.backend.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class MovieService {

	private final MovieRepository movieRepository;

	@Autowired
	public MovieService(MovieRepository movieRepository) {
		this.movieRepository = movieRepository;
	}

	/**
	 * Calcula os intervalos entre os anos dos filmes para cada produtor.
	 *
	 * @return Um objeto ResponseDto que contém os produtores com o menor e o maior intervalo.
	 */
	public ResponseDto calculateIntervals() {
		List<Movie> movies = getMoviesWinners();

		return buildResponseDto(
				calculateInterval(movies, false),
				calculateInterval(movies, true));
	}

	/**
	 * Constrói um objeto ResponseDto a partir das listas de produtores com menor e maior intervalo.
	 *
	 * @param producerWithMinInterval Lista de produtores com menor intervalo.
	 * @param producerWithMaxInterval Lista de produtores com maior intervalo.
	 * @return Um objeto ResponseDto.
	 */
	private ResponseDto buildResponseDto(List<ProducerResponseDto> producerWithMinInterval, List<ProducerResponseDto> producerWithMaxInterval) {
		return ResponseDto.builder()
				.min(producerWithMinInterval)
				.max(producerWithMaxInterval)
				.build();
	}

	/**
	 * Calcula os intervalos entre os anos dos filmes para cada produtor.
	 *
	 * @param movies      Lista de filmes.
	 * @param maxInterval Define se deve calcular o intervalo máximo (true) ou mínimo (false).
	 * @return Uma lista de objetos ProducerResponseDto com os intervalos calculados.
	 */
	private List<ProducerResponseDto> calculateInterval(List<Movie> movies, boolean maxInterval) {
		Map<String, List<Movie>> moviesByProducer = groupMoviesByProducer(movies);
		List<ProducerResponseDto> response = new ArrayList<>();
		int intervalThreshold = maxInterval ? -1 : Integer.MAX_VALUE;

		for (Map.Entry<String, List<Movie>> entry : moviesByProducer.entrySet()) {
			List<Movie> relevantMovies = entry.getValue();
			if (relevantMovies.size() >= 2) {
				relevantMovies.sort(Comparator.comparingInt(Movie::getYearMovie));

				for (int i = 1; i < relevantMovies.size(); i++) {
					int interval = relevantMovies.get(i).getYearMovie() - relevantMovies.get(i - 1).getYearMovie();

					if (shouldUpdateInterval(maxInterval, interval, intervalThreshold)) {
						intervalThreshold = interval;
						ProducerResponseDto producerResponseDto = createProducerResponse(entry.getKey(), interval, relevantMovies.get(i - 1), relevantMovies.get(i));
						response.clear(); // Limpar a lista para adicionar apenas o produtor com menor/maior intervalo
						response.add(producerResponseDto);
					} else if (interval == intervalThreshold) {
						ProducerResponseDto producerResponseDto = createProducerResponse(entry.getKey(), interval, relevantMovies.get(i - 1), relevantMovies.get(i));
						response.add(producerResponseDto);
					}
				}
			}
		}
		return response;
	}

	/**
	 * Verifica se o intervalo deve ser atualizado com base no tipo de intervalo (maxInterval), intervalo calculado e threshold atual.
	 *
	 * @param maxInterval        Define se é um intervalo máximo (true) ou mínimo (false).
	 * @param interval           Intervalo calculado.
	 * @param intervalThreshold  Threshold atual.
	 * @return true se o intervalo deve ser atualizado, caso contrário, false.
	 */
	private boolean shouldUpdateInterval(boolean maxInterval, int interval, int intervalThreshold) {
		return (maxInterval && interval > intervalThreshold) || (!maxInterval && interval < intervalThreshold);
	}

	/**
	 * Cria um objeto ProducerResponseDto com informações sobre o produtor e o intervalo.
	 *
	 * @param producer        Nome do produtor.
	 * @param interval        Intervalo calculado.
	 * @param previousMovie   Filme anterior.
	 * @param followingMovie  Filme seguinte.
	 * @return Um objeto ProducerResponseDto.
	 */
	private ProducerResponseDto createProducerResponse(String producer, int interval, Movie previousMovie, Movie followingMovie) {
		return ProducerResponseDto.builder()
				.producer(producer)
				.interval(interval)
				.previousWin(previousMovie.getYearMovie())
				.followingWin(followingMovie.getYearMovie())
				.build();
	}

	/**
	 * Agrupa a lista de filmes por produtor em um Map, levando em consideração que o producer esteja envolvido em outro filme juntamente com outro produtor.
	 *
	 * @param movies Lista de filmes.
	 * @return Um mapa que agrupa os filmes pelo nome do produtor.
	 */
	private Map<String, List<Movie>> groupMoviesByProducer(List<Movie> movies) {
		return movies.stream()
				.flatMap(movie -> splitProducers(movie).stream().map(producer -> new AbstractMap.SimpleEntry<>(producer, movie)))
				.collect(Collectors.groupingBy(
						AbstractMap.SimpleEntry::getKey,
						Collectors.mapping(AbstractMap.SimpleEntry::getValue, Collectors.toList())
				));
	}

	/**
	 * Divide os nomes dos produtores se houver mais de um, considerando vírgulas e " and ".
	 *
	 * @param movie Filme.
	 * @return Lista de nomes de produtores.
	 */
	private List<String> splitProducers(Movie movie) {
		List<String> producers = new ArrayList<>();
		String[] producerNames = movie.getProducers().split(",| and ");

		for (String producer : producerNames) {
			producers.add(producer.trim());
		}

		return producers;
	}

	/**
	 * Obtém a lista de filmes vencedores.
	 *
	 * @return Uma lista de objetos MovieWinnerResponseDto.
	 */
	private List<Movie> getMoviesWinners() {
		return movieRepository.findAllByWinnerTrue();
	}

	/**
	 * Obtém a lista de filmes vencedores, agrupa-os por produtor e retorna um mapa onde as chaves são nomes de produtores
	 * e os valores são listas de filmes vencedores associados a cada produtor.
	 *
	 * @return Um mapa que associa nomes de produtores a listas de filmes vencedores.
	 */
	public Map<String, List<Movie>> groupMoviesWinnersByProducer() {
		List<Movie> winners = getMoviesWinners();
		return groupMoviesByProducer(winners);
	}

}
