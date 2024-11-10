package com.example.internintelligence_movieapidevelopment.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WatchlistRequestDto {
    private Long userId;
    private Long movieId;
}
