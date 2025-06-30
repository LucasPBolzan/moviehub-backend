package com.project.movieapi.controller;

import com.project.movieapi.model.Review;
import com.project.movieapi.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @GetMapping("/movie/{movieId}")
    public List<Review> getReviewsByMovie(@PathVariable Long movieId) {
        return reviewService.getReviewsByMovie(movieId);
    }

    @PostMapping
    public Review createReview(@RequestBody Review review) {
        System.out.println("Recebendo avaliação:");
        System.out.println("Filme ID: " + review.getMovieId());
        System.out.println("Usuário: " + review.getUserName());
        System.out.println("Nota: " + review.getRating());
        System.out.println("Comentário: " + review.getComment());

        return reviewService.createReview(review);
    }
}
