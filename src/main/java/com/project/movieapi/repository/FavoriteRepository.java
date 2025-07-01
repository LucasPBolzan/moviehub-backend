package com.project.movieapi.repository;

import com.project.movieapi.model.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    List<Favorite> findByUserName(String userName);
    Optional<Favorite> findByMovieIdAndUserName(Long movieId, String userName);
    void deleteByMovieIdAndUserName(Long movieId, String userName);
}
