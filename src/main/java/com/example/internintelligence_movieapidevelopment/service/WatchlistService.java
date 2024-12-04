package com.example.internintelligence_movieapidevelopment.service;

import com.example.internintelligence_movieapidevelopment.dao.entity.Movie;
import com.example.internintelligence_movieapidevelopment.dao.entity.User;
import com.example.internintelligence_movieapidevelopment.dao.entity.Watchlist;
import com.example.internintelligence_movieapidevelopment.dao.repository.*;
import com.example.internintelligence_movieapidevelopment.dto.response.WatchlistResponseDto;
import com.example.internintelligence_movieapidevelopment.exception.AlreadyExistException;
import com.example.internintelligence_movieapidevelopment.exception.ResourceNotFoundException;
import com.example.internintelligence_movieapidevelopment.mapper.WatchlistMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class WatchlistService {

    private final WatchlistRepository watchlistRepository;
    private final WatchlistMapper watchlistMapper;
    private final MovieRepository movieRepository;
    private final UserRepository userRepository;


    public WatchlistResponseDto addToWatchlist(Integer movieId) {
        String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
        log.info("Attempting to add movie ID '{}' to user's - {} watchlist.", movieId, currentUser);
        Movie movie = movieRepository.findById(movieId).orElseThrow(() -> {
            log.warn("Failed to add movie to watchlist: Movie with ID {} not found", movieId);
            return new ResourceNotFoundException("MOVIE_NOT_FOUND");
        });

        User user = userRepository.findByUsername(currentUser).orElseThrow(() -> {
            log.warn("Failed to add movie to watchlist: user '{}' not found", currentUser);
            return new ResourceNotFoundException("USER_NOT_FOUND");
        });

        if (watchlistRepository.existsByUserAndMovie(user, movie)) {
            log.warn("Movie already exists in the watchlist.");
            throw new AlreadyExistException("MOVIE_ALREADY_EXISTS_IN_WATCHLIST");
        }

        Watchlist watchlist = new Watchlist();
        watchlist.setUser(user);
        watchlist.setMovie(movie);
        watchlistRepository.save(watchlist);

        log.info("Successfully added movie ID '{}' to user '{}' watchlist", movieId, currentUser);
        return watchlistMapper.toDto(watchlist);
    }


    public List<WatchlistResponseDto> getWatchlist() {
        String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
        log.info("Attempting to get user's watchlist - '{}' ", currentUser);
        User user = userRepository.findByUsername(currentUser).orElseThrow(() -> {
            log.warn("Failed to get watchlist: user '{}' not found", currentUser);
            return new ResourceNotFoundException("USER_NOT_FOUND");
        });
        List<Watchlist> watchlist = watchlistRepository.findAllByUser(user);
        return watchlist
                .stream()
                .map(watchlistMapper::toDto)
                .toList();
    }


    public void deleteFromWatchlist(Integer movieId) {
        String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
        log.info("Attempting to delete movie ID '{}' from user's watchlist-{}", movieId, currentUser);

        Movie movie = movieRepository.findById(movieId).orElseThrow(() -> {
            log.error("Failed to delete movie from watchlist: movie ID '{}' not found", movieId);
            return new ResourceNotFoundException("MOVIE_NOT_FOUND");
        });

        User user = userRepository.findByUsername(currentUser).orElseThrow(() -> {
            log.error("Failed to delete movie from watchlist: user '{}' not found", currentUser);
            return new ResourceNotFoundException("USER_NOT_FOUND");
        });

        Watchlist watchlist = watchlistRepository.findByUserAndMovie(user, movie).orElseThrow(() -> {
            log.error("Failed to find movie ID '{}' in user '{}' watchlist", movieId, currentUser);
            return new ResourceNotFoundException("WATCHLIST_ENTRY_NOT_FOUND");
        });

        watchlistRepository.delete(watchlist);
        log.info("Successfully deleted movie ID '{}' from user '{}' watchlist", movieId, currentUser);
    }


}
