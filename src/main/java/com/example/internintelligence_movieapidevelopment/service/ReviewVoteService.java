package com.example.internintelligence_movieapidevelopment.service;

import com.example.internintelligence_movieapidevelopment.dao.entity.Review;
import com.example.internintelligence_movieapidevelopment.dao.entity.ReviewVote;
import com.example.internintelligence_movieapidevelopment.dao.entity.User;
import com.example.internintelligence_movieapidevelopment.dao.repository.ReviewRepository;
import com.example.internintelligence_movieapidevelopment.dao.repository.ReviewVoteRepository;
import com.example.internintelligence_movieapidevelopment.dao.repository.UserRepository;
import com.example.internintelligence_movieapidevelopment.dto.request.ReviewVoteRequestDto;
import com.example.internintelligence_movieapidevelopment.exception.AlreadyExistException;
import com.example.internintelligence_movieapidevelopment.exception.ResourceNotFound;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ReviewVoteService {
    private final ReviewRepository reviewRepository;
    private final ReviewVoteRepository reviewVoteRepository;
    private final UserRepository userRepository;


    public void voteOnReview(Long reviewId, ReviewVoteRequestDto requestDto) {

    }

    private void updateReviewVoteCount(Review review, ReviewVoteRequestDto requestDto) {
    }
}

