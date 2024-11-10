package com.example.internintelligence_movieapidevelopment.dao.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "watchlist")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Watchlist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private User user;
}
//    @ManyToMany
//    @JoinTable(
//            name = "watchlist_movie",
//            joinColumns = @JoinColumn(name = "watchlist_id"),
//            inverseJoinColumns = @JoinColumn(name = "movie_id"),
//            uniqueConstraints = @UniqueConstraint(columnNames = {"watchlist_id", "movie_id"})
//    )
//    private List<WatchlistMovie> watchlistMovies;
