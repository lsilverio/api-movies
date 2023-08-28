package br.com.teste.backend.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class HealthCheckDto {

    private String application;
    private int status;
    private String message;

}
