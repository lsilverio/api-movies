package br.com.teste.backend;

import br.com.teste.backend.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class ApplicationStartup implements ApplicationRunner {

    private final FileService fileService;

    @Autowired
    public ApplicationStartup(FileService fileService) {
        this.fileService = fileService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        fileService.loadDataFromCSV();
    }
}
