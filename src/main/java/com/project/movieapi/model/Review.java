package com.project.movieapi.model;

import jakarta.persistence.*;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Data
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String author;
    private String comment;
    private int rating;

    @ManyToOne
    @JoinColumn(name = "movie_id")
    @JsonIgnore // Evita o loop Movie ↔ Review ↔ Movie...
    private Movie movie;
}
