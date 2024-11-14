package com.example.internintelligence_movieapidevelopment.controller;

import com.example.internintelligence_movieapidevelopment.dto.request.ReviewVoteRequestDto;
import com.example.internintelligence_movieapidevelopment.service.ReviewVoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewVoteController {

    private final ReviewVoteService reviewVoteService;


    @PostMapping("/{reviewId}/vote")
    public ResponseEntity<String> voteReview(
            @PathVariable Long reviewId,
            @RequestBody ReviewVoteRequestDto requestDto
    ) {
        reviewVoteService.voteOnReview(reviewId, requestDto);
        return ResponseEntity.ok("Vote registered successfully.");
    }
}
