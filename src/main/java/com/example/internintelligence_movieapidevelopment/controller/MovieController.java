package com.example.internintelligence_movieapidevelopment.controller;

import com.example.internintelligence_movieapidevelopment.dto.request.MovieFilterDto;
import com.example.internintelligence_movieapidevelopment.dto.request.MovieRequestDto;
import com.example.internintelligence_movieapidevelopment.dto.response.MovieOverviewDto;
import com.example.internintelligence_movieapidevelopment.dto.response.PersonOverviewDto;
import com.example.internintelligence_movieapidevelopment.service.MovieService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/movie")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;

    @Operation(summary = "Retrieve a movie by ID", description = "This endpoint attempts to retrieve the movie from the local database.")
    @GetMapping("/{id}")
    public MovieOverviewDto getMovieByID(@PathVariable Long id) {
        return movieService.getMovieById(id);
    }

    @Operation(summary = "Retrieve the cast of the movie from TMDB.")
    @GetMapping("/{id}/cast")
    public List<PersonOverviewDto> getCast(@PathVariable Long id) {
        return movieService.getCastOfMovie(id);
    }

    @Operation(summary = "Retrieve top 100 popular movies for the week from the local database.", description = "This endpoint retrieves the top 100 popular movies for the week from the local database, which is updated weekly. User also can apply filter for searching.")
    @GetMapping("/get-all")
    public Page<MovieOverviewDto> getMovies(@PageableDefault(sort = "popularity", direction = Sort.Direction.DESC) Pageable pageable, MovieFilterDto movieFilterDto) {
        return movieService.getMovies(pageable, movieFilterDto);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/add")
    public MovieOverviewDto addMovie(@Validated @RequestBody MovieRequestDto requestDto) {
        return movieService.addMovie(requestDto);
    }

    @PutMapping("/edit/{id}")
    public MovieOverviewDto editMovie(
            @PathVariable Long id,
            @Valid @RequestBody MovieRequestDto movieRequestDto
    ) {
        return movieService.editMovie(id, movieRequestDto);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/delete/{id}")
    public void deleteMovie(@PathVariable Long id) {
        movieService.deleteMovie(id);
    }

}
