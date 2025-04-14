package com.movie.movieCatalogue.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FavoriteMovie {
    @Id
    private Long id;
    
    private String title;
    
    @Column(length = 1000)
    private String overview;
    
    @Column(name = "release_date")
    private LocalDate releaseDate;
    
    private Double voteAverage;
    
    @Column(length = 255)
    private String posterPath;
}
