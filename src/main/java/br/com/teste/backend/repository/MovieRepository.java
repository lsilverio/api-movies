package br.com.teste.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.teste.backend.entity.Movie;

import java.util.List;

/**
 * Interface de repositório para a entidade Movie.
 */
@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    /**
     * Retorna uma lista de todos os filmes com a marcação 'winner' como verdadeira.
     *
     * @return Uma lista de filmes vencedores.
     */
    List<Movie> findAllByWinnerTrue();
}
