package com.example.internintelligence_movieapidevelopment.dao.repository;

import com.example.internintelligence_movieapidevelopment.dao.entity.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface MovieRepository extends JpaRepository<Movie, Long>, JpaSpecificationExecutor<Movie> {
    Optional<Movie> findById(Long id);

    //method for handling n+1 problem
//    @EntityGraph(attributePaths = {"genres", "cast"})
    Page<Movie> findAll(Specification<Movie> specification, Pageable pageable);

    //second method of handling n+1 problem
//    @Query("SELECT m FROM Movie m LEFT JOIN FETCH m.cast LEFT JOIN FETCH m.genres")
    Page<Movie> findAll(Pageable pageable);

    boolean existsByTitleAndReleaseDate(String title, LocalDate releaseDate);

    void deleteById(Long id);

    List<Movie> findMovieByCastId(Long id);

    Page<Movie> findByGenresId(Long id, Pageable pageable);
}
