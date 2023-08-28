package br.com.teste.backend.dto;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.List;

@Getter
public class ErrorResponseDto {

    private final HttpStatus status;
    private final List<String> errors;

    public ErrorResponseDto(HttpStatus status, List<String> errors) {
        super();
        this.status = status;
        this.errors = errors;
    }

    public ErrorResponseDto(HttpStatus status, String error) {
        super();
        this.status = status;
        errors = Collections.singletonList(error);
    }

}
