package com.project.movieapi.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "movies")
@Data
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private Integer year;
    private Double rating;

    // Adicione esta propriedade que estava faltando
    @Column(columnDefinition = "TEXT")
    private String genres; // Armazenar como string separada por vírgulas

    // Outros campos se necessário
    private String poster;
    private String description;
}
