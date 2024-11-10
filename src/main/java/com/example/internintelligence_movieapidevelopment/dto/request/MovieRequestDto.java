package com.example.internintelligence_movieapidevelopment.dto.request;

import com.example.internintelligence_movieapidevelopment.enums.AgeRating;
import com.example.internintelligence_movieapidevelopment.validation.ValidationGroups;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieRequestDto {
    @NotBlank(message = "Tittle can't be blank.",groups = ValidationGroups.Create.class)
    @Size(min = 2, max = 100, message = "Tittle must be between 2 and 100 characters.")
    private String title;

    @NotBlank(message = "Overview can't be blank",groups = ValidationGroups.Create.class)
    @Size(max = 500, message = "Overview cannot exceed 500 characters.")
    private String overview;

    @NotNull(message = "Release date is required",groups = ValidationGroups.Create.class)
    @PastOrPresent(message = "Released date can't be in the future")
    private LocalDate releaseDate;

    @NotNull(message = "Age rating is required",groups = ValidationGroups.Create.class)
    private AgeRating ageRating;

    @Min(30)
    @Max(240)
    @NotNull(message = "Duration is required",groups = ValidationGroups.Create.class)
    private Integer duration;

    @NotNull(message = "People id can't be null",groups = ValidationGroups.Create.class)
    private Set<Long> peopleId;

    @NotNull(message = "Genre id can't be null",groups = ValidationGroups.Create.class)
    private Set<Long> genresId;
}
