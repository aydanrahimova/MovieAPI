package com.example.internintelligence_movieapidevelopment.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewRequestDto {
    //securityden sonra hec userId lazim olmayacaq
    private Long userId;     // ID of the user submitting the review
    @Size(min = 500, message = " It needs to contain at least 500 characters")
    private String content;
    @Min(1)
    @Max(10)
    @NotNull(message = "Rating is required")
    private Integer rating;
    private Boolean hasSpoiler;

}
