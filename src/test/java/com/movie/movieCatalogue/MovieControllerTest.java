package com.movie.movieCatalogue;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.springframework.ui.Model;

import com.movie.movieCatalogue.controller.MovieController;
import com.movie.movieCatalogue.model.Movie;
import com.movie.movieCatalogue.service.TmdbService;

class MovieControllerTest {

    private MovieController movieController;
    private TmdbService tmdbService;
    private Model model;

    @BeforeEach
    void setUp() {
        tmdbService = mock(TmdbService.class);
        model = mock(Model.class);
        movieController = new MovieController(tmdbService, null);
    }

    @Test
    void home() {
        // Arrange
        Movie movie = new Movie();
        movie.setId(1L);
        movie.setTitle("Movie Title");
        List<Movie> movieList = Collections.singletonList(movie);
        when(tmdbService.getPopularMovies()).thenReturn(movieList);

        // Act
        String result = movieController.home(model);

        // Assert
        verify(model).addAttribute("movies", movieList);
        assert(result.equals("home"));
    }
}
