package br.com.teste.backend.controller;

import br.com.teste.backend.dto.HealthCheckDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
public class HealthCheckController {

    @Operation(summary = "Health check")
    @ApiResponse(responseCode = "200", description = "Retorna o status da API")
    @GetMapping
    public ResponseEntity<Object> check() {
        return ResponseEntity.ok().body(buildHealthCheckDto());
    }

    private static HealthCheckDto buildHealthCheckDto() {

        return HealthCheckDto.builder()
                .application("teste-backend")
                .status(HttpStatus.OK.value())
                .message("UP")
                .build();
    }

}
