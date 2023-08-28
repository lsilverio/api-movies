package br.com.teste.backend.service;

import br.com.teste.backend.dto.ProducerResponseDto;
import br.com.teste.backend.dto.ResponseDto;
import br.com.teste.backend.entity.Movie;
import br.com.teste.backend.repository.MovieRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class MovieService {

	private final MovieRepository movieRepository;

	@Autowired
	public MovieService(MovieRepository movieRepository) {
		this.movieRepository = movieRepository;
	}

	public ResponseDto calculateIntervals() {

		log.info("Find all movies by true winner");
		List<Movie> movies = movieRepository.findAllByWinnerTrue();
		log.info("movies winner true size=[{}]", movies.size());

		return buildResponseDto(
				calculateInterval(movies, false),
				calculateInterval(movies, true));
	}

	private static ResponseDto buildResponseDto(List<ProducerResponseDto> producerWithMinInterval, List<ProducerResponseDto> producerWithMaxInterval) {
		return ResponseDto.builder()
				.min(producerWithMinInterval)
				.max(producerWithMaxInterval)
				.build();
	}

	private List<ProducerResponseDto> calculateInterval(List<Movie> movies, boolean maxInterval) {
		Map<String, List<Movie>> moviesByProducer = groupMoviesByProducer(movies);

		List<ProducerResponseDto> response = new ArrayList<>();
		int intervalThreshold = maxInterval ? -1 : Integer.MAX_VALUE;

		for (Map.Entry<String, List<Movie>> entry : moviesByProducer.entrySet()) {
			List<Movie> producerMovies = entry.getValue();
			List<Movie> relevantMovies = maxInterval ? producerMovies : producerMovies.stream().filter(Movie::isWinner).collect(Collectors.toList());

			if (relevantMovies.size() >= 2) {
				relevantMovies.sort(Comparator.comparingInt(Movie::getYearMovie));

				for (int i = 1; i < relevantMovies.size(); i++) {
					int interval = relevantMovies.get(i).getYearMovie() - relevantMovies.get(i - 1).getYearMovie();

					if ((maxInterval && interval > intervalThreshold) || (!maxInterval && interval < intervalThreshold)) {
						intervalThreshold = interval;
						ProducerResponseDto producerResponseDto = createProducerResponse(entry.getKey(), interval, relevantMovies.get(i - 1), relevantMovies.get(i));
						response.clear();
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

	private ProducerResponseDto createProducerResponse(String producer, int interval, Movie previousMovie, Movie followingMovie) {
		ProducerResponseDto producerResponseDto = new ProducerResponseDto();
		producerResponseDto.setProducer(producer);
		producerResponseDto.setInterval(interval);
		producerResponseDto.setPreviousWin(previousMovie.getYearMovie());
		producerResponseDto.setFollowingWin(followingMovie.getYearMovie());
		return producerResponseDto;
	}

	private static Map<String, List<Movie>> groupMoviesByProducer(List<Movie> movies) {
		movies.sort(Comparator.comparing(Movie::getProducers));
		return movies.stream().collect(Collectors.groupingBy(Movie::getProducers));
	}
}
