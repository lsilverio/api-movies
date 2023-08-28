package br.com.teste.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ProducerResponseDto {

    private String producer;
    private int interval;
    private int previousWin;
    private int followingWin;

}
