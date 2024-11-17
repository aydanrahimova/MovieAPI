package com.example.internintelligence_movieapidevelopment.dao.repository;

import com.example.internintelligence_movieapidevelopment.dao.entity.Movie;
import com.example.internintelligence_movieapidevelopment.dao.entity.Watchlist;
import com.example.internintelligence_movieapidevelopment.dao.entity.WatchlistMovie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WatchlistMovieRepository extends JpaRepository<WatchlistMovie,Long> {
    boolean existsByWatchlistAndMovie(Watchlist watchlist, Movie movie);
    boolean existsByWatchlist(Watchlist watchlist);
    List<WatchlistMovie> findByWatchlist(Watchlist watchlist);
    Page<WatchlistMovie> findByWatchlist(Watchlist watchlist, Pageable pageable);

    List<WatchlistMovie> findByWatchlistId(Long watchlistId, Sort sort);

    Optional<WatchlistMovie> findByWatchlistAndMovie(Watchlist watchlist, Movie movie);
}
