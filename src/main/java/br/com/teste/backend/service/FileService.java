package br.com.teste.backend.service;

import br.com.teste.backend.entity.Movie;
import br.com.teste.backend.exception.FileCsvNotFoundException;
import br.com.teste.backend.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FileService {

    private static final String CSV_FILE_PATH = "movie/movielist.csv";
    private final MovieRepository movieRepository;
    private final ResourceLoader resourceLoader;

    @Autowired
    public FileService(MovieRepository movieRepository, ResourceLoader resourceLoader) {
        this.movieRepository = movieRepository;
        this.resourceLoader = resourceLoader;
    }

    /**
     * Carrega dados do arquivo CSV e os salva no repositório de filmes.
     * @throws IOException se houver um erro durante a leitura ou gravação do arquivo CSV.
     */
    public void loadDataFromCSV() throws IOException {
        List<String[]> csvData = readCsvData();
        List<Movie> movies = buildMovies(csvData);
        saveMoviesToRepository(movies);
    }

    /**
     * Obtém o arquivo CSV como um objeto File.
     * @return O arquivo CSV.
     * @throws IOException se o arquivo CSV não for encontrado.
     */
    private File getCsvFile() throws IOException {
        Resource resource = resourceLoader.getResource(CSV_FILE_PATH);

        if (!resource.exists()) {
            throw new FileCsvNotFoundException("File csv not found: " + CSV_FILE_PATH);
        }

        return resource.getFile();
    }

    /**
     * Lê os dados do arquivo CSV e retorna uma lista de arrays de strings representando as linhas do arquivo.
     * @return Uma lista de arrays de strings contendo os dados do CSV.
     * @throws IOException se houver um erro durante a leitura do arquivo CSV.
     */
    private List<String[]> readCsvData() throws IOException {
        Resource resource = new ClassPathResource(CSV_FILE_PATH);

        if (!resource.exists()) {
            throw new FileCsvNotFoundException("File csv not found: " + CSV_FILE_PATH);
        }

        try (BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
            br.readLine(); // Primeira linha do conteúdo é cabeçalho
            return br.lines().map(line -> line.split(";")).collect(Collectors.toList());
        }
    }

    /**
     * Converte os dados do CSV em objetos Movie.
     * @param csvData Uma lista de arrays de strings representando os dados do CSV.
     * @return Uma lista de objetos Movie.
     */
    private List<Movie> buildMovies(List<String[]> csvData) {
        return csvData.stream()
                .map(this::buildMovie)
                .collect(Collectors.toList());
    }

    /**
     * Converte uma linha do CSV em um objeto Movie.
     * @param fields Um array de strings representando os campos do filme no CSV.
     * @return Um objeto Movie.
     */
    private Movie buildMovie(String[] fields) {
        int year = Integer.parseInt(fields[0]);
        String title = fields[1];
        String studios = fields[2];
        String producer = fields[3];
        boolean winner = fields.length > 4 && "yes".equalsIgnoreCase(fields[4]);

        return Movie.builder()
                .yearMovie(year)
                .title(title)
                .studios(studios)
                .producers(producer)
                .winner(winner)
                .build();
    }

    /**
     * Salva a lista de filmes no repositório de filmes.
     * @param movies Uma lista de objetos Movie a serem salvos.
     */
    private void saveMoviesToRepository(List<Movie> movies) {
        movieRepository.saveAll(movies);
    }

}
