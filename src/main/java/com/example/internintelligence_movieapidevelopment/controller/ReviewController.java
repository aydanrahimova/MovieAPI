package com.example.internintelligence_movieapidevelopment.controller;

import com.example.internintelligence_movieapidevelopment.dto.request.ReviewRequestDto;
import com.example.internintelligence_movieapidevelopment.dto.response.ReviewResponseDto;
import com.example.internintelligence_movieapidevelopment.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/movies/{movieId}/reviews")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @GetMapping("/{reviewId}")
    public ReviewResponseDto getReviewByID(
            @PathVariable Long movieId,
            @PathVariable Long reviewId
    ){
        return reviewService.getReviewByID(movieId,reviewId);
    }

    @GetMapping
    public Page<ReviewResponseDto> getReviews(
            @PathVariable Long movieId,
            @RequestParam int page,
            @RequestParam int size
    ){
        return reviewService.getReviews(movieId,page,size);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ReviewResponseDto addReview(
            @PathVariable(name = "movieId") Long movieId,
            @Valid @RequestBody ReviewRequestDto requestDto
    ){
        return reviewService.addReview(movieId,requestDto);
    }

    @PatchMapping("/{reviewId}")
    public ReviewResponseDto editReview(
            @PathVariable(name = "movieId") Long movieId,
            @PathVariable(name = "reviewId") Long reviewId,
            @Valid @RequestBody ReviewRequestDto updateDto
    ){
        return reviewService.editReview(movieId,reviewId,updateDto);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{reviewId}")
    public void deleteReview(
            @PathVariable(name = "movieId") Long movieId,
            @PathVariable(name = "reviewId") Long reviewId
    ){
        reviewService.deleteByID(movieId,reviewId);
    }

}
