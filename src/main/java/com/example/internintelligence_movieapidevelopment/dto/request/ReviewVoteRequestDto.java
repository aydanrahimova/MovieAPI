package com.example.internintelligence_movieapidevelopment.dto.request;

import com.example.internintelligence_movieapidevelopment.enums.VoteType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewVoteRequestDto {
    private Long userId;
    private VoteType voteType;
}
