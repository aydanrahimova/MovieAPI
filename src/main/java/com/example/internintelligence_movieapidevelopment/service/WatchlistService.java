package com.example.internintelligence_movieapidevelopment.service;

import com.example.internintelligence_movieapidevelopment.dao.entity.Movie;
import com.example.internintelligence_movieapidevelopment.dao.entity.User;
import com.example.internintelligence_movieapidevelopment.dao.entity.Watchlist;
import com.example.internintelligence_movieapidevelopment.dao.entity.WatchlistMovie;
import com.example.internintelligence_movieapidevelopment.dao.repository.MovieRepository;
import com.example.internintelligence_movieapidevelopment.dao.repository.UserRepository;
import com.example.internintelligence_movieapidevelopment.dao.repository.WatchlistMovieRepository;
import com.example.internintelligence_movieapidevelopment.dao.repository.WatchlistRepository;
import com.example.internintelligence_movieapidevelopment.dto.response.WatchlistMovieResponseDto;
import com.example.internintelligence_movieapidevelopment.dto.response.WatchlistResponseDto;
import com.example.internintelligence_movieapidevelopment.exception.AlreadyExistException;
import com.example.internintelligence_movieapidevelopment.exception.ResourceNotFound;
import com.example.internintelligence_movieapidevelopment.mapper.WatchlistMapper;
import com.example.internintelligence_movieapidevelopment.mapper.WatchlistMovieMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class WatchlistService {

    private final WatchlistRepository watchlistRepository;
    private final WatchlistMapper watchlistMapper;
    private final WatchlistMovieRepository watchlistMovieRepository;
    private final WatchlistMovieMapper watchlistMovieMapper;
    private final MovieRepository movieRepository;
    private final UserRepository userRepository;


    public WatchlistResponseDto addToWatchlist(Long userId, Long movieId) {
        log.info("Attempting add movie ID '{}' to user's ID '{}' watchlist.", movieId, userId);

        Movie movie = movieRepository.findById(movieId).orElseThrow(() -> {
            log.error("Failed to add movie to watchlist: movie ID '{}' not found", movieId);
            return new ResourceNotFound("MOVIE_NOT_FOUND");
        });

        User user = userRepository.findById(userId).orElseThrow(() -> {
            log.error("Failed to add movie to watchlist: user ID '{}' not found", userId);
            return new ResourceNotFound("USER_NOT_FOUND");
        });

        Watchlist watchlist = user.getWatchlist();

        if (watchlistMovieRepository.existsByWatchlistAndMovie(watchlist, movie)) {
            log.error("Movie already exists in the watchlist.");
            throw new AlreadyExistException("MOVIE_ALREADY_EXISTS");
        }

        // Create and save the WatchlistMovie entity to establish the link
        WatchlistMovie watchlistMovie = new WatchlistMovie(null, watchlist, movie, LocalDateTime.now());
        watchlistMovieRepository.save(watchlistMovie);

        log.info("Successfully added movie ID '{}' to user ID '{}' watchlist", movieId, userId);
        return watchlistMapper.toDto(watchlist);
    }


    public WatchlistResponseDto getWatchlist(Long userId, Pageable pageable) {
        log.info("Attempting to get user's ID '{}' watchlist", userId);

        User user = userRepository.findById(userId).orElseThrow(() -> {
            log.error("Failed to get watchlist: user ID '{}' not found", userId);
            return new ResourceNotFound("USER_NOT_FOUND");
        });

        Watchlist watchlist = user.getWatchlist();
        Page<WatchlistMovie> watchlistMoviePage = watchlistMovieRepository.findByWatchlist(watchlist, pageable);
        List<WatchlistMovieResponseDto> watchlistMovieResponseDto = watchlistMoviePage.stream().map(watchlistMovieMapper::toDto).toList();
        WatchlistResponseDto watchlistResponseDto = new WatchlistResponseDto();
        watchlistResponseDto.setMovies(watchlistMovieResponseDto);
        watchlistResponseDto.setPageSize(watchlistMoviePage.getSize());
        watchlistResponseDto.setCurrentPage(watchlistMoviePage.getNumber());
        watchlistResponseDto.setTotalPages(watchlistMoviePage.getTotalPages());


        return watchlistResponseDto;
    }


    public void deleteFromWatchlist(Long userId, Long movieId) {
        log.info("Attempting delete movie ID '{}' to user ID '{}' watchlist", movieId, userId);

        Movie movie = movieRepository.findById(movieId).orElseThrow(() -> {
            log.error("Failed to delete movie to watchlist: movie ID '{}' not found", movieId);
            return new ResourceNotFound("MOVIE_NOT_FOUND");
        });

        User user = userRepository.findById(userId).orElseThrow(() -> {
            log.error("Failed to delete movie from watchlist: user ID '{}' not found", userId);
            return new ResourceNotFound("USER_NOT_FOUND");
        });

        Watchlist watchlist = user.getWatchlist();
        WatchlistMovie watchlistMovie = watchlistMovieRepository.findByWatchlistAndMovie(watchlist, movie).orElseThrow(() -> {
            log.info("Failed to delete movie from watchlist: movie ID '{}' not found in watchlist", movieId);
            return new ResourceNotFound("MOVIE_NOT_FOUND_IN_WATCHLIST");
        });

        watchlistMovieRepository.delete(watchlistMovie);

        log.info("Successfully deleted movie ID '{}' from user ID '{}' watchlist", movieId, userId);
    }

}
