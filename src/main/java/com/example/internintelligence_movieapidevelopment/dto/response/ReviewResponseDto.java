package com.example.internintelligence_movieapidevelopment.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewResponseDto {
    private String userName;
    private Integer rating;
    private String content;
    private LocalDateTime createTime;
    private Integer helpfulVotes;
    private Integer unhelpfulVotes;
}
