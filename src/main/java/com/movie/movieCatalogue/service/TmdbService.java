package com.movie.movieCatalogue.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.movie.movieCatalogue.model.Movie;

@Service
public class TmdbService {

    @Value("${tmdb.api.key}")
    private String apiKey;

    @Value("${tmdb.api.url}")
    private String apiUrl;

    // Get popular movies
    public List<Movie> getPopularMovies() {
        String url = apiUrl + "/movie/popular?api_key=" + apiKey;

        Map<String, Object> response = new RestTemplate().getForObject(url, Map.class);

        if (response == null || !response.containsKey("results")) {
            return Collections.emptyList();
        }

        List<Map<String, Object>> results = (List<Map<String, Object>>) response.get("results");
        List<Movie> movies = new ArrayList<>();
        for (Map<String, Object> result : results) {
            Movie movie = new Movie();
            movie.setId(((Number) result.get("id")).longValue());
            movie.setTitle((String) result.get("title"));
            movie.setOverview((String) result.get("overview"));
            movie.setPoster_path((String) result.get("poster_path"));
            movie.setRelease_date((String) result.get("release_date"));
            movie.setVote_average(((Number) result.get("vote_average")).doubleValue());
            movies.add(movie);
        }
        return movies;
    }

    // Get movie details by ID
    public Movie getMovieDetails(Long id) {
        String url = apiUrl + "/movie/" + id + "?api_key=" + apiKey;
        return new RestTemplate().getForObject(url, Movie.class);
    }

    // Search movies by title
    public List<Movie> searchMovies(String query) {
        String url = apiUrl + "/search/movie?api_key=" + apiKey + "&query=" + query;

        Map<String, Object> response = new RestTemplate().getForObject(url, Map.class);

        if (response == null || !response.containsKey("results")) {
            return Collections.emptyList();
        }

        List<Map<String, Object>> results = (List<Map<String, Object>>) response.get("results");
        List<Movie> movies = new ArrayList<>();
        for (Map<String, Object> result : results) {
            Movie movie = new Movie();
            movie.setId(((Number) result.get("id")).longValue());
            movie.setTitle((String) result.get("title"));
            movie.setOverview((String) result.get("overview"));
            movie.setPoster_path((String) result.get("poster_path"));
            movie.setRelease_date((String) result.get("release_date"));
            movie.setVote_average(((Number) result.get("vote_average")).doubleValue());
            movies.add(movie);
        }
        return movies;
    }
}
