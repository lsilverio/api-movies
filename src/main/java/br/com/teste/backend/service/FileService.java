package br.com.teste.backend.service;

import br.com.teste.backend.entity.Movie;
import br.com.teste.backend.exception.FileCsvNotFoundException;
import br.com.teste.backend.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FileService {

    private static final String DIRECTORY_FILE = "classpath:movie/movielist.csv";
    private final MovieRepository movieRepository;
    private final ResourceLoader resourceLoader;

    @Autowired
    public FileService(MovieRepository movieRepository, ResourceLoader resourceLoader) {
        this.movieRepository = movieRepository;
        this.resourceLoader = resourceLoader;
    }

    public void loadDataFromCSV() throws IOException {
        File csvFile = getCsvFile();
        List<String[]> csvData = readCsvData(csvFile);
        List<Movie> movies = buildMovies(csvData);
        saveMoviesToRepository(movies);
    }

    private File getCsvFile() throws IOException {
        Resource resource = resourceLoader.getResource(DIRECTORY_FILE);

        if (!resource.exists()) {
            throw new FileCsvNotFoundException("File csv not found: " + DIRECTORY_FILE);
        }

        return resource.getFile();
    }

    private List<String[]> readCsvData(File csvFile) throws IOException {
        List<String[]> csvData = new ArrayList<>();

        try (BufferedReader br = Files.newBufferedReader(csvFile.toPath())) {
            // Descarta o cabeçalho do conteúdo do arquivo
            br.readLine();

            String line;
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(";");
                csvData.add(fields);
            }
        }

        return csvData;
    }

    private List<Movie> buildMovies(List<String[]> csvData) {
        return csvData.stream()
                .map(this::buildMovie)
                .collect(Collectors.toList());
    }

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

    private void saveMoviesToRepository(List<Movie> movies) {
        movieRepository.saveAll(movies);
    }

}
