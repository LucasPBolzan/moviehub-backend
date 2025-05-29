package com.project.movieapi.service;

import com.project.movieapi.model.Review;
import com.project.movieapi.model.Movie;
import com.project.movieapi.repository.MovieRepository;
import com.project.movieapi.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private MovieRepository movieRepository;

    public List<Review> getReviewsByMovie(Long movieId) {
        return reviewRepository.findByMovieId(movieId);
    }

    public Review createReview(Review review) {
        Movie movie = movieRepository.findById(review.getMovie().getId())
                .orElseThrow(() -> new RuntimeException("Filme não encontrado"));
        review.setMovie(movie);
        Review savedReview = reviewRepository.save(review);

        List<Review> reviews = reviewRepository.findByMovieId(movie.getId());
        double averageRating = reviews.stream()
                .mapToInt(Review::getRating)
                .average()
                .orElse(0.0);
        movie.setAverageRating(averageRating);
        movieRepository.save(movie);

        return savedReview;
    }
}