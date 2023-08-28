package br.com.teste.backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
public class Movie {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int yearMovie;
    private String title;
    private String studios;
    private String producers;
    private boolean winner;

}
