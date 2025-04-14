package com.movie.movieCatalogue.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.movie.movieCatalogue.model.FavoriteMovie;
import com.movie.movieCatalogue.model.Movie;
import com.movie.movieCatalogue.repository.FavoriteMovieRepository;
import com.movie.movieCatalogue.service.TmdbService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MovieController {

    private final TmdbService tmdbService;
    private final FavoriteMovieRepository repo;

    private static final Logger logger = LoggerFactory.getLogger(MovieController.class);

    // Home page displaying popular movies
    @GetMapping("/")
    public String home(Model model) {
        try {
            List<Movie> movies = tmdbService.getPopularMovies();
            model.addAttribute("movies", movies);
            return "home";
        } catch (Exception e) {
            logger.error("Failed to load popular movies", e);
            return "error";
        }
    }

    // Movie details page
    @GetMapping("/movie/{id}")
    public String movieDetails(@PathVariable Long id, Model model) {
        try {
            Movie movie = tmdbService.getMovieDetails(id);
            boolean isFavorited = repo.existsById(id);
            model.addAttribute("movie", movie);
            model.addAttribute("isFavorited", isFavorited);
            return "details";
        } catch (Exception e) {
            logger.error("Error loading movie details for ID: {}", id, e);
            return "error";
        }
    }

    // Add movie to favorites
    @PostMapping("/favorite/{id}")
    public String addToFavorites(@PathVariable Long id) {
        try {
            Movie movie = tmdbService.getMovieDetails(id);

            if (movie == null || movie.getRelease_date() == null) {
                logger.warn("Invalid movie or missing release date for ID: {}", id);
                return "error";
            }

            if (!repo.existsById(id)) {
                DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
                LocalDate releaseDate = LocalDate.parse(movie.getRelease_date(), formatter);

                FavoriteMovie fav = new FavoriteMovie(
                    movie.getId(),
                    movie.getTitle(),
                    movie.getOverview(),
                    releaseDate,
                    movie.getVote_average(),
                    movie.getPoster_path()
                );
                repo.save(fav);
                logger.info("Added movie to favorites: {}", movie.getTitle());
            } else {
                logger.info("Movie already in favorites: {}", movie.getTitle());
            }

            return "redirect:/movie/" + id;
        } catch (Exception e) {
            logger.error("Error adding movie to favorites with ID: {}", id, e);
            return "error";
        }
    }

    // Remove movie from favorites
    @PostMapping("/unfavorite/{id}")
    public String removeFromFavorites(@PathVariable Long id) {
        try {
            repo.deleteById(id);
            logger.info("Removed movie from favorites with ID: {}", id);

            if (repo.findAll().isEmpty()) {
                return "redirect:/";
            }
            return "redirect:/favorites";
        } catch (Exception e) {
            logger.error("Error removing movie from favorites with ID: {}", id, e);
            return "error";
        }
    }

    // Display list of favorite movies
    @GetMapping("/favorites")
    public String favorites(Model model) {
        try {
            List<FavoriteMovie> favoriteMovies = repo.findAll();
            if (favoriteMovies.isEmpty()) {
                model.addAttribute("message", "No favorite movies found.");
            } else {
                model.addAttribute("favorites", favoriteMovies);
            }
            return "favorites";
        } catch (Exception e) {
            logger.error("Error fetching favorite movies", e);
            return "error";
        }
    }

    @GetMapping("/search")
    public String searchMovies(@RequestParam("query") String query, Model model) {
        try {
            List<Movie> movies = tmdbService.searchMovies(query);
            
            if (movies.isEmpty()) {
                model.addAttribute("message", "No movies found for your search: " + query);
            }
            
            model.addAttribute("movies", movies);
            return "home";
        } catch (Exception e) {
            logger.error("Error searching movies with query: {}", query, e);
            return "error";
        }
    }
    
}
