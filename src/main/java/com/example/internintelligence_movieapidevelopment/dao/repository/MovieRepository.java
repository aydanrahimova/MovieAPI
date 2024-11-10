package com.example.internintelligence_movieapidevelopment.dao.repository;

import com.example.internintelligence_movieapidevelopment.dao.entity.Movie;
import com.example.internintelligence_movieapidevelopment.dao.entity.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    Optional<Movie> findById(Long id);

    boolean existsByTitleAndReleaseDate(String title, LocalDate releaseDate);

    void deleteById(Long id);

    Page<Movie> findAll(Pageable pageable);

//    Page<Movie> findByGenres_Name(String name, Pageable pageable);

    List<Movie> findMovieByPeople_Id(Long id);

    Page<Movie> findMovieByGenres_Id(Long id,Pageable pageable);

}
