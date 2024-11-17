package com.example.internintelligence_movieapidevelopment.dto.response;

import com.example.internintelligence_movieapidevelopment.enums.AgeRating;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieOverviewDto {
    private String title;
    private LocalDate releaseDate;
    private Double IMDbRating;
    private AgeRating ageRating;
    private Integer duration;

}
