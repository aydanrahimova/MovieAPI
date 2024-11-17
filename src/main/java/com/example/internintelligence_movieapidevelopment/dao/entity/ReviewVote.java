package com.example.internintelligence_movieapidevelopment.dao.entity;

import com.example.internintelligence_movieapidevelopment.enums.VoteType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "review_votes")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewVote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "review_id")
    private Review review;
    @Enumerated(EnumType.STRING)
    private VoteType voteType;
}
