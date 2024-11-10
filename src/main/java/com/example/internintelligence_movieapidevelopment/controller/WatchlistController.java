package com.example.internintelligence_movieapidevelopment.controller;

import com.example.internintelligence_movieapidevelopment.dto.response.WatchlistResponseDto;
import com.example.internintelligence_movieapidevelopment.service.WatchlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/watchlist")
@RequiredArgsConstructor
public class WatchlistController {

    private final WatchlistService watchlistService;

    @GetMapping("/{userId}")
    public WatchlistResponseDto getWatchlist(@PathVariable Long userId){
        return watchlistService.getWatchlist(userId);
    }

    @PostMapping("/{userId}/{movieId}")
    public void addToWatchlist(
            @PathVariable Long userId,
            @PathVariable Long movieId
    ){
        watchlistService.addToWatchlist(userId,movieId);
    }

//    @GetMapping("/{userId}")
//    public Page<WatchlistResponseDto> getWatchlist(@PathVariable Long userId){
//        return watchlistService.getWatchlist(userId);
//    }

    @DeleteMapping("{userId}/{movieId}")
    public void deleteFromWatchlist(
            @PathVariable Long userId,
            @PathVariable Long movieId
    ){
        watchlistService.deleteFromWatchlist(userId,movieId);
    }


}
