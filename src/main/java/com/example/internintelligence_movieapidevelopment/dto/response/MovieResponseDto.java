package com.example.internintelligence_movieapidevelopment.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieResponseDto {
    @JsonProperty("tmdb_id")
    private Long tmdbId;
    private String title;
    private String overview;
    @JsonProperty("release_date")
    private LocalDate releaseDate;
    private Boolean adult;
    private Integer runtime;
    private List<PersonOverviewDto> cast;
    private List<GenreOverviewDto> genres;
    @JsonProperty("vote_average")
    private Double voteAverage;
}
