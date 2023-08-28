package br.com.teste.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDto {

    @Builder.Default
    private List<ProducerResponseDto> min = new ArrayList<>();
    @Builder.Default
    private List<ProducerResponseDto> max = new ArrayList<>();

}
