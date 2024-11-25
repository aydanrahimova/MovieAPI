package com.example.internintelligence_movieapidevelopment.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GenreOverviewDto {
    @JsonProperty("id")
    private Long tmdbId;
    private String name;

}
