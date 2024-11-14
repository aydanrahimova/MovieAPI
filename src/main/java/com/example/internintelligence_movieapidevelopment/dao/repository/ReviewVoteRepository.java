package com.example.internintelligence_movieapidevelopment.dao.repository;

import com.example.internintelligence_movieapidevelopment.dao.entity.Review;
import com.example.internintelligence_movieapidevelopment.dao.entity.ReviewVote;
import com.example.internintelligence_movieapidevelopment.dao.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewVoteRepository extends JpaRepository<ReviewVote,Long> {
    Optional<ReviewVote> findByUserAndReview(User user, Review review);
    List<ReviewVote> findByReview(Review review);
}
