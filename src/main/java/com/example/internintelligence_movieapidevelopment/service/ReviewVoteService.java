package com.example.internintelligence_movieapidevelopment.service;

import com.example.internintelligence_movieapidevelopment.dao.entity.Review;
import com.example.internintelligence_movieapidevelopment.dao.entity.ReviewVote;
import com.example.internintelligence_movieapidevelopment.dao.entity.User;
import com.example.internintelligence_movieapidevelopment.dao.repository.ReviewRepository;
import com.example.internintelligence_movieapidevelopment.dao.repository.ReviewVoteRepository;
import com.example.internintelligence_movieapidevelopment.dao.repository.UserRepository;
import com.example.internintelligence_movieapidevelopment.dto.request.ReviewVoteRequestDto;
import com.example.internintelligence_movieapidevelopment.exception.ResourceNotFoundException;
import com.example.internintelligence_movieapidevelopment.mapper.ReviewVoteMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ReviewVoteService {
    private final ReviewRepository reviewRepository;
    private final ReviewVoteRepository reviewVoteRepository;
    private final UserRepository userRepository;
    private final ReviewVoteMapper reviewVoteMapper;


    public void voteOnReview(Long reviewId, ReviewVoteRequestDto requestDto) {

        User user = userRepository.findById(requestDto.getUserId()).orElseThrow(() -> {
            log.error("Failed to vote on review: user ID '{}' doesn't exist", requestDto.getUserId());
            return new ResourceNotFoundException("USER_NOT_FOUND");
        });
        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> {
            log.error("Failed to vote on review: review ID '{}' doesn't exist", reviewId);
            return new ResourceNotFoundException("REVIEW_NOT_FOUND");
        });

        ReviewVote vote = reviewVoteMapper.toEntity(requestDto);
        vote.setUser(user);
        vote.setReview(review);

        reviewVoteRepository.save(vote);

    }

    private void updateReviewVoteCount(Review review, ReviewVoteRequestDto requestDto) {
    }
}

