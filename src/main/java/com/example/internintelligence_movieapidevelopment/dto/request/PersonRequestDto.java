package com.example.internintelligence_movieapidevelopment.dto.request;

import com.example.internintelligence_movieapidevelopment.enums.PersonRole;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonRequestDto {
    @NotBlank(message = "Full name for actor is required")
    @Pattern(regexp = "^[A-Za-z ]+$", message = "Only alphabetic characters and spaces are allowed")
    private String fullName;

    @NotNull(message = "Role for person is required")
    private PersonRole role;

    @NotNull(message = "Birth date for actor is required")
    @Past(message = "Birth date of actor can't be the present or future")
    private LocalDate birthDate;

    @NotBlank(message = "Biography is required")
    @Size(max = 500,message = "Biography cannot exceed 500 characters.")
    private String biography;
}
