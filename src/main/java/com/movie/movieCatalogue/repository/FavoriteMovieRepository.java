package com.movie.movieCatalogue.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.movie.movieCatalogue.model.FavoriteMovie;

public interface FavoriteMovieRepository extends JpaRepository<FavoriteMovie, Long> {
}
