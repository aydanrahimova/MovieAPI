package com.example.internintelligence_movieapidevelopment.dao.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "watchlist")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Watchlist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "watchlist")
    private User user;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "watchlist")
    private List<WatchlistMovie> movies;
}
