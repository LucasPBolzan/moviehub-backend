package com.project.movieapi.repository;


import com.project.movieapi.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    List<Movie> findByGenresContaining(String genre);
    List<Movie> findByTitleContainingIgnoreCase(String title);
}
