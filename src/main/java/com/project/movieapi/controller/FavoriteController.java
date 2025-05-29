package com.project.movieapi.controller;

import com.project.movieapi.model.Movie;
import com.project.movieapi.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/favorites")
public class FavoriteController {

    @Autowired
    private FavoriteService favoriteService;

    @PostMapping("/{userId}/add/{movieId}")
    public void addFavorite(@PathVariable Long userId, @PathVariable Long movieId) {
        favoriteService.addFavorite(userId, movieId);
    }

    @PostMapping("/{userId}/remove/{movieId}")
    public void removeFavorite(@PathVariable Long userId, @PathVariable Long movieId) {
        favoriteService.removeFavorite(userId, movieId);
    }

    @GetMapping("/{userId}")
    public List<Movie> getFavorites(@PathVariable Long userId) {
        return favoriteService.getFavorites(userId);
    }
}