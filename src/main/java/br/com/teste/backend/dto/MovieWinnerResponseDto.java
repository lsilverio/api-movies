package br.com.teste.backend.dto;

import br.com.teste.backend.entity.Movie;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class MovieWinnerResponseDto {

    private Long id;
    private int yearMovie;
    private String title;
    private String studios;
    private String producers;
    private boolean winner;

    public MovieWinnerResponseDto(Movie data) {
        this.id = data.getId();
        this.yearMovie = data.getYearMovie();
        this.title = data.getTitle();
        this.studios = data.getStudios();
        this.producers = data.getProducers();
        this.winner = data.isWinner();
    }

}
