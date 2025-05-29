package com.project.movieapi.model;

import jakarta.persistence.*;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

@Entity
@Data
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String posterUrl;
    private String synopsis;
    private String genres;
    private int releaseYear;
    private double averageRating;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    @JsonIgnore // Evita loop com Review → Movie → Review...
    private List<Review> reviews;

    @ManyToMany(mappedBy = "favorites")
    @JsonIgnore // Evita loop com User → Movie → User...
    private List<User> users;
}
