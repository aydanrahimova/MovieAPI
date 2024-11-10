package com.example.internintelligence_movieapidevelopment.dao.entity;

import com.example.internintelligence_movieapidevelopment.enums.AgeRating;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "movie")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String overview;
    private LocalDate releaseDate;
    @Column(name = "imdb_rating")
    private Double IMDbRating;//average hesablayir commentlerden
    @Enumerated(EnumType.STRING)
    private AgeRating ageRating;
    private Integer duration;
    @ManyToMany
    @JoinTable(
            name = "genre_movie",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private List<Genre> genres;

    @OneToMany(mappedBy = "movie",fetch = FetchType.LAZY)
    private List<Review> reviews;

    @ManyToMany
    @JoinTable(
            name = "person_movie",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "person_id")
    )
    private List<Person> people;
}
