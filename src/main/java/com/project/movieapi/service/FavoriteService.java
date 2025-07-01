package com.project.movieapi.service;

import com.project.movieapi.model.Favorite;
import com.project.movieapi.repository.FavoriteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class FavoriteService {

    @Autowired
    private FavoriteRepository favoriteRepository;

    public List<Favorite> getFavoritesByUser(String userName) {
        return favoriteRepository.findByUserName(userName);
    }

    public Favorite addFavorite(Long movieId, String userName) {
        if (favoriteRepository.findByMovieIdAndUserName(movieId, userName).isPresent()) {
            throw new IllegalArgumentException("Filme já está nos favoritos");
        }
        Favorite favorite = new Favorite(movieId, userName);
        return favoriteRepository.save(favorite);
    }

    @Transactional
    public void removeFavorite(Long movieId, String userName) {
        favoriteRepository.deleteByMovieIdAndUserName(movieId, userName);
    }

    public boolean isFavorite(Long movieId, String userName) {
        return favoriteRepository.findByMovieIdAndUserName(movieId, userName).isPresent();
    }
}
