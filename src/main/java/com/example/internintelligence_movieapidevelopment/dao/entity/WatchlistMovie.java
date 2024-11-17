package com.example.internintelligence_movieapidevelopment.dao.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "watchlist_movie")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WatchlistMovie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "watchlist_id")
    private Watchlist watchlist;

    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime addAt; // Timestamp for when this movie was added to the watchlist
}
