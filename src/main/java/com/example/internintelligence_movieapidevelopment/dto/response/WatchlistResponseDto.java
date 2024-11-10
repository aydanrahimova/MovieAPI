package com.example.internintelligence_movieapidevelopment.dto.response;

import com.example.internintelligence_movieapidevelopment.dao.entity.Movie;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WatchlistResponseDto {

    private List<MovieOverviewDto> movies;
}
