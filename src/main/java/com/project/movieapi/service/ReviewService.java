package com.project.movieapi.service;

import com.project.movieapi.model.Review;
import com.project.movieapi.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    public List<Review> getReviewsByMovie(Long movieId) {
        return reviewRepository.findByMovieIdOrderByIdDesc(movieId);
    }

    public Review createReview(Review review) {
        return reviewRepository.save(review);
    }
}
