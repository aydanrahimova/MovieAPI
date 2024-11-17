package com.example.internintelligence_movieapidevelopment.controller;

import com.example.internintelligence_movieapidevelopment.dto.response.WatchlistResponseDto;
import com.example.internintelligence_movieapidevelopment.service.WatchlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/watchlist")
@RequiredArgsConstructor
public class WatchlistController {

    private final WatchlistService watchlistService;


    //needs security but now enter with userId
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{userId}/{movieId}")
    public WatchlistResponseDto addToWatchlist(@PathVariable Long userId, @PathVariable Long movieId) {
        return watchlistService.addToWatchlist(userId, movieId);
    }

    @GetMapping("/{userId}")
    public WatchlistResponseDto getWatchlist(
            @PathVariable Long userId,
            @PageableDefault Pageable pageable
    ) {
        return watchlistService.getWatchlist(userId, pageable);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{userId}/{movieId}")
    public void deleteFromWatchlist(@PathVariable Long userId, @PathVariable Long movieId) {
        watchlistService.deleteFromWatchlist(userId, movieId);
    }


}
