package com.example.internintelligence_movieapidevelopment.dto.response;

import com.example.internintelligence_movieapidevelopment.enums.AgeRating;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieResponseDto {
    private String title;
    private String overview;
    private LocalDate releaseDate;
    private AgeRating ageRating;
    private Integer duration;
    private List<PersonOverviewDto> peopleOverview;
    private List<GenreOverviewDto> genres;
    private Double IMDbRating;
}
