package com.example.internintelligence_movieapidevelopment.dao.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "review")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Review {//commentda filmin id-si olmalidi
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    private Integer rating;
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createTime;
    //principal lazimdi user ucun
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;
    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL)
    private List<ReviewVote> votes;
    @NotNull(message = "Has spoiler is required.")
    private Boolean hasSpoiler;
}
