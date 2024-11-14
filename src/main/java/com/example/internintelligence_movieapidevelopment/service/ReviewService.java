package com.example.internintelligence_movieapidevelopment.service;

import com.example.internintelligence_movieapidevelopment.dao.entity.Movie;
import com.example.internintelligence_movieapidevelopment.dao.entity.Review;
import com.example.internintelligence_movieapidevelopment.dao.entity.ReviewVote;
import com.example.internintelligence_movieapidevelopment.dao.entity.User;
import com.example.internintelligence_movieapidevelopment.dao.repository.MovieRepository;
import com.example.internintelligence_movieapidevelopment.dao.repository.ReviewRepository;
import com.example.internintelligence_movieapidevelopment.dao.repository.ReviewVoteRepository;
import com.example.internintelligence_movieapidevelopment.dao.repository.UserRepository;
import com.example.internintelligence_movieapidevelopment.dto.request.ReviewRequestDto;
import com.example.internintelligence_movieapidevelopment.dto.response.ReviewResponseDto;
import com.example.internintelligence_movieapidevelopment.enums.VoteType;
import com.example.internintelligence_movieapidevelopment.exception.AlreadyExistException;
import com.example.internintelligence_movieapidevelopment.exception.ResourceNotFound;
import com.example.internintelligence_movieapidevelopment.mapper.ReviewMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;
    private final UserRepository userRepository;
    private final MovieRepository movieRepository;
    private final ReviewVoteRepository reviewVoteRepository;

    public ReviewResponseDto addReview(Long movieId, ReviewRequestDto requestDto) {
        log.info("Attempting to add review to movie with ID: {}", movieId);

        Movie movie = movieRepository.findById(movieId).orElseThrow(() -> {
            log.error("Movie with ID {} not found", movieId);
            return new ResourceNotFound("MOVIE_NOT_FOUND");
        });

        User user = userRepository.findById(requestDto.getUserId()).orElseThrow(() -> {
            log.error("User with ID {} not found", requestDto.getUserId());
            return new ResourceNotFound("USER_NOT_FOUND");
        });

        if (reviewRepository.existsByUserAndMovie(user, movie)) {
            log.error("User with ID {} has already reviewed movie with ID {}", user.getId(), movie.getId());
            throw new AlreadyExistException("REVIEW_ALREADY_ADDED_BY_USER");
        }

        Review review = reviewMapper.toEntity(requestDto);
        review.setMovie(movie);
        review.setUser(user);

        log.info("Updating IMDb rating for movie with ID: {}", movie.getId());
        updateIMDbRating(movie, review.getRating());

        reviewRepository.save(review);
        log.info("Review successfully added for movie with ID: {}", movie.getId());

        return reviewMapper.toDto(review);
    }

    public ReviewResponseDto editReview(Long movieId, Long reviewId, ReviewRequestDto updateDto) {
        log.info("Attempting edit a review ID '{}' to a movie ID '{}' ",reviewId,movieId);

        Movie movie = movieRepository.findById(movieId).orElseThrow(()->{
            log.error("Movie ID '{}' not found",movieId);
            return new ResourceNotFound("MOVIE_NOT_FOUND");
        });

        User user = userRepository.findById(updateDto.getUserId()).orElseThrow(()->{
            log.error("User ID '{}' not found",updateDto.getUserId());
            return new ResourceNotFound("USER_NOT_FOUND");
        });


//        // Validate user who owns the review to prevent unauthorized edits
//        if (!review.getUser().getId().equals(updateDto.getUserId())) {
//            throw new UnauthorizedActionException("You can only edit your own reviews.");
//        }

        Review review = reviewRepository.findById(reviewId).orElseThrow(()->{
            log.error("Failed to edit a review: review ID '{}' not found.",reviewId);
            return new ResourceNotFound("REVIEW_NOT_FOUND");
        });

        Review editedReview = reviewMapper.toEntity(updateDto);
        editedReview.setUser(user);
        editedReview.setMovie(movie);
        editedReview.setCreateTime(LocalDateTime.now());

        reviewRepository.save(editedReview);
        log.info("Successfully edited.");

        return reviewMapper.toDto(review);
    }

    public ReviewResponseDto getReviewByID(Long movieId, Long reviewId) {
        log.info("Attempting get review ID '{}'",reviewId);

        if(!movieRepository.existsById(movieId)){
            log.error("Failed to get review: movie ID '{}' not found",movieId);
            throw new ResourceNotFound("MOVIE_NOT_FOUND");
        }

        Review review = reviewRepository.findById(reviewId).orElseThrow(()->{
            log.error("Failed to get review: review ID '{}' not found",reviewId);
            return new ResourceNotFound("REVIEW_NOT_FOUND");
        });

        List<ReviewVote> votes = reviewVoteRepository.findByReview(review);


        long helpfulVotes = votes
                .stream()
                .filter(vote ->vote.getVoteType()== VoteType.HELPFUL)
                .count();

        long unhelpfulVotes = votes
                .stream()
                .filter(vote -> vote.getVoteType()==VoteType.UNHELPFUL)
                .count();

        ReviewResponseDto reviewResponseDto = reviewMapper.toDto(review);
        reviewResponseDto.setHelpfulVotes(helpfulVotes);
        reviewResponseDto.setUnhelpfulVotes(unhelpfulVotes);


//        reviewResponseDto.setUserName();//security

//        for()
//        reviewResponseDto.setHelpfulVotes();
        return reviewResponseDto;
    }
    public Page<ReviewResponseDto> getReviews(Long movieId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Review> reviews = reviewRepository.findAllByMovieId(movieId, pageable);
        //her review ucun like-larin saylari

        return reviews.map(reviewMapper::toDto);
    }


    public void deleteByID(Long movieId, Long reviewId) {//ancaq adim ya da user ozu
        log.info("Attempting delete review ID '{}'",reviewId);

        if(!movieRepository.existsById(movieId)){
            log.error("Failed to delete review: movie ID '{}' not found",movieId);
            throw new ResourceNotFound("MOVIE_NOT_FOUND");
        }

        if(!reviewRepository.existsById(reviewId)){
            log.error("Failed to delete review: review ID '{}' not found",reviewId);
            throw new ResourceNotFound("REVIEW_NOT_FOUND");
        }

        reviewRepository.deleteById(reviewId);
        log.info("Successfully deleted.");
    }

    private void updateIMDbRating(Movie movie, Integer rating) {
        if (movie.getIMDbRating() == null) {
            movie.setIMDbRating(Double.valueOf(rating));
        } else {
            movie.setIMDbRating(calculateNewIMDbRating(movie.getIMDbRating(), rating));
        }
    }

    private double calculateNewIMDbRating(double currentIMDbRating, int newRating) {
        return (currentIMDbRating + newRating) / 2;
    }
}
