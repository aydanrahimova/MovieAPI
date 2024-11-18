package com.example.internintelligence_movieapidevelopment.dto.response;

import com.example.internintelligence_movieapidevelopment.enums.AgeRating;
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
    private String title;
    private String overview;
    @JsonProperty("release_date")
    private LocalDate releaseDate;
    private AgeRating ageRating;
    private Integer duration;
    private List<PersonOverviewDto> peopleOverview;
    private List<GenreOverviewDto> genres;
    @JsonProperty("vote_average")
    private Double IMDbRating;
}
