package com.example.internintelligence_movieapidevelopment.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonResponseDto {
    private String fullName;
    private LocalDate birthDate;
    private String biography;
    private List<MovieOverviewDto> movies;
}
