package com.example.internintelligence_movieapidevelopment.dao.repository;

import com.example.internintelligence_movieapidevelopment.dao.entity.Genre;
import com.example.internintelligence_movieapidevelopment.dao.entity.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface MovieRepository extends JpaRepository<Movie, Long>, JpaSpecificationExecutor<Movie> {
    Optional<Movie> findById(Long id);


    boolean existsByTitleAndReleaseDate(String title, LocalDate releaseDate);

    void deleteById(Long id);

//    Page<Movie> findAll(Specification specification,Pageable pageable);
@EntityGraph(attributePaths = {"genres","cast"}) // Specify associations to fetch eagerly
Page<Movie> findAll(Specification<Movie> specification, Pageable pageable);


    Page<Movie> findByGenresId(Long id, Pageable pageable);

    List<Movie> findMovieByCastId(Long id);

    Page<Movie> findMovieByGenresId(Long id,Pageable pageable);

//    @Query("SELECT m FROM Movie m " +
//            "JOIN FETCH m.cast " +
//            "JOIN FETCH m.genres")
//    Page<Movie> findAllWithRelations(Specification<Movie> specification,Pageable pageable);

}
