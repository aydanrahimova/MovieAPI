package com.example.internintelligence_movieapidevelopment.service;

import com.example.internintelligence_movieapidevelopment.client.TmdbClient;
import com.example.internintelligence_movieapidevelopment.dao.repository.*;
import com.example.internintelligence_movieapidevelopment.dto.response.WatchlistResponseDto;
import com.example.internintelligence_movieapidevelopment.exception.ResourceNotFoundException;
import com.example.internintelligence_movieapidevelopment.mapper.GenreMapper;
import com.example.internintelligence_movieapidevelopment.mapper.MovieMapper;
import com.example.internintelligence_movieapidevelopment.mapper.WatchlistMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class WatchlistService {

    private final MovieMapper movieMapper;
    @Value("${tmdb.api-key}")
    private String apiKey;

    @Value("${tmdb.default-language}")
    private String language;

    private final WatchlistRepository watchlistRepository;
    private final GenreRepository genreRepository;
    private final GenreMapper genreMapper;
    private final TmdbClient tmdbClient;
    private final WatchlistMapper watchlistMapper;
    private final MovieRepository movieRepository;
    private final MovieService movieService;
    private final UserRepository userRepository;


//    public WatchlistResponseDto addToWatchlist(Long userId, Long movieId) {
//        log.info("Attempting to add movie ID '{}' to user's ID '{}' watchlist.", movieId, userId);
//        log.info("Firstly attempting to find movie from local DB.");
//
//        Movie movie = movieRepository.findByTmdbId(movieId).orElseGet(() -> {
//            log.info("Movie not found in local DB, so it tries to get movie from TMDB.");
//            return movieMapper.toEntity(movieService.fetchAndSaveMovie(movieId));
//        });
//
//        System.out.println(movie);
//        System.out.println(movie.getId());
//        System.out.println(movie.getTitle());
//
//        User user = userRepository.findById(userId).orElseThrow(() -> {
//            log.error("Failed to add movie to watchlist: user ID '{}' not found", userId);
//            return new ResourceNotFound("USER_NOT_FOUND");
//        });
//
//        if (watchlistRepository.existsByUserAndMovie(user, movie)) {
//            log.warn("Movie already exists in the watchlist.");
//            throw new AlreadyExistException("MOVIE_ALREADY_EXISTS_IN_WATCHLIST");
//        }
//
//        Watchlist watchlist = new Watchlist();
//        watchlist.setUser(user);
//        watchlist.setMovie(movie);
//        watchlistRepository.save(watchlist);
//
//        log.info("Successfully added movie ID '{}' to user ID '{}' watchlist", movieId, userId);
//        return watchlistMapper.toDto(watchlist);
//    }


    public Page<WatchlistResponseDto> getWatchlist(Long userId, Pageable pageable) {
        log.info("Attempting to get user's ID '{}' watchlist", userId);
//       return watchlistRepository.findAllByUser(userRepository.findById(userId).orElseThrow(()->{
//            log.warn("User not found.");
//            return new ResourceNotFound("USER NOT FOUND");
//        }),pageable).map(watchlistMapper::toDto);
        Page<WatchlistResponseDto> result = watchlistRepository.findAllByUser(userRepository.findById(userId)
                .orElseThrow(() -> {
                    log.warn("User not found.");
                    return new ResourceNotFoundException("USER NOT FOUND");
                }), pageable).map(watchlistMapper::toDto);

        if (result.isEmpty()) {
            log.info("User's watchlist is empty.");
        }
        return result;

    }


//    public void deleteFromWatchlist(Long userId, Long movieId) {
//        log.info("Attempting delete movie ID '{}' to user ID '{}' watchlist", movieId, userId);
//
//        Movie movie = movieRepository.findById(movieId).orElseThrow(() -> {
//            log.error("Failed to delete movie to watchlist: movie ID '{}' not found", movieId);
//            return new ResourceNotFound("MOVIE_NOT_FOUND");
//        });
//
//        User user = userRepository.findById(userId).orElseThrow(() -> {
//            log.error("Failed to delete movie from watchlist: user ID '{}' not found", userId);
//            return new ResourceNotFound("USER_NOT_FOUND");
//        });
//
//        Watchlist watchlist = user.getWatchlist();
//        WatchlistMovie watchlistMovie = watchlistMovieRepository.findByWatchlistAndMovie(watchlist, movie).orElseThrow(() -> {
//            log.info("Failed to delete movie from watchlist: movie ID '{}' not found in watchlist", movieId);
//            return new ResourceNotFound("MOVIE_NOT_FOUND_IN_WATCHLIST");
//        });
//
//        watchlistMovieRepository.delete(watchlistMovie);
//
//        log.info("Successfully deleted movie ID '{}' from user ID '{}' watchlist", movieId, userId);
//    }

}
