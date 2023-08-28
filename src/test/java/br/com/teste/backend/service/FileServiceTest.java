package br.com.teste.backend.service;

import br.com.teste.backend.repository.MovieRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
public class FileServiceTest {

    private static final String CORRECT_DIRECTORY_FILE = "src/test/resources/movie/movielist.csv";

    @Autowired
    private FileService fileService;

    @Autowired
    private MovieRepository movieRepository;

    @MockBean
    private ResourceLoader resourceLoader;

    @MockBean
    private Resource resource;

    @Test
    public void testLoadDataFromCSV() throws IOException {
        when(resourceLoader.getResource(anyString())).thenReturn(resource);
        when(resource.exists()).thenReturn(true);
        when(resource.getFile()).thenReturn(new File(CORRECT_DIRECTORY_FILE));
        fileService.loadDataFromCSV();
        assertTrue(movieRepository.count() > 0);
    }

}

