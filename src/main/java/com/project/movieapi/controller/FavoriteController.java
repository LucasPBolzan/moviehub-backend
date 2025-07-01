package com.project.movieapi.controller;

import com.project.movieapi.model.Favorite;
import com.project.movieapi.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/favorites")
@CrossOrigin(origins = "http://localhost:8081")
public class FavoriteController {

    @Autowired
    private FavoriteService favoriteService;

    @GetMapping("/user/{userName}")
    public ResponseEntity<List<Favorite>> getFavoritesByUser(@PathVariable String userName) {
        List<Favorite> favorites = favoriteService.getFavoritesByUser(userName);
        return ResponseEntity.ok(favorites);
    }

    @PostMapping
    public ResponseEntity<Favorite> addFavorite(@RequestBody Map<String, Object> request) {
        Long movieId = Long.valueOf(request.get("movieId").toString());
        String userName = request.get("userName").toString();

        try {
            Favorite favorite = favoriteService.addFavorite(movieId, userName);
            return ResponseEntity.status(HttpStatus.CREATED).body(favorite);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/movie/{movieId}/user/{userName}")
    public ResponseEntity<Void> removeFavorite(@PathVariable Long movieId, @PathVariable String userName) {
        favoriteService.removeFavorite(movieId, userName);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/check/movie/{movieId}/user/{userName}")
    public ResponseEntity<Map<String, Boolean>> checkFavorite(@PathVariable Long movieId, @PathVariable String userName) {
        boolean isFavorite = favoriteService.isFavorite(movieId, userName);
        return ResponseEntity.ok(Map.of("isFavorite", isFavorite));
    }
}
