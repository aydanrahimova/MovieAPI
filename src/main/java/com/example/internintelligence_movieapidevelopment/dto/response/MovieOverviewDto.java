package com.example.internintelligence_movieapidevelopment.dto.response;

import com.example.internintelligence_movieapidevelopment.enums.AgeRating;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieOverviewDto {
    @JsonProperty("id")
    private Long tmdbId;
    private String title;
    @JsonProperty("release_date")
    private LocalDate releaseDate;
    @JsonProperty("vote_average")
    private Double voteAverage;
    private String overview;
//    private Boolean adult;
    private Integer runtime;
    private List<GenreOverviewDto> genres;
//    private List<PersonOverviewDto> cast;

}
