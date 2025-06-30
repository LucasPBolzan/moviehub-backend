package com.project.movieapi.repository;

import com.project.movieapi.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByMovieIdOrderByIdDesc(Long movieId);
}
