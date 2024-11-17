package com.example.internintelligence_movieapidevelopment.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WatchlistResponseDto {
    private List<WatchlistMovieResponseDto> movies;
    private int totalPages;
    private int currentPage;
    private int pageSize;
}
