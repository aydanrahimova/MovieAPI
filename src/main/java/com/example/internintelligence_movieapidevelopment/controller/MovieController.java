package com.example.internintelligence_movieapidevelopment.controller;

import com.example.internintelligence_movieapidevelopment.client.clientResponse.Credits;
import com.example.internintelligence_movieapidevelopment.dto.request.MovieFilterDto;
import com.example.internintelligence_movieapidevelopment.dto.request.MovieRequestDto;
import com.example.internintelligence_movieapidevelopment.dto.response.MovieOverviewDto;
import com.example.internintelligence_movieapidevelopment.service.MovieService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/movie")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;

//    @GetMapping("/bygenre/{id}")
//    public List<MovieOverviewDto> getMoviesByGenre(@PathVariable Long id,int page){
//        return movieService.getMoviesByGenre(id,page);
//    }


    @Operation(summary = "Retrieve a movie by TMDB ID", description = "This endpoint attempts to retrieve the movie from the local database first. If the movie is not found, it fetches the data from the TMDB API and just display without saving in local DB.")
    @GetMapping("/{tmdbId}")
    public MovieOverviewDto getMovieByID(@PathVariable Long tmdbId) {
        return movieService.getOrFetchMovie(tmdbId);
    }

    @Operation(summary = "Retrieve the cast of the movie from TMDB.")
    @GetMapping("/{tmdbId}/cast")
    public Credits getCast(@PathVariable Long tmdbId) {
        return movieService.getCastOfMovie(tmdbId);
    }

    @Operation(summary = "Retrieve top 100 popular movies for the week from the local database.", description = "This endpoint retrieves the top 100 popular movies for the week from the local database, which is updated weekly. User also can apply filter for searching.")
    @GetMapping("/get-all")
    public Page<MovieOverviewDto> getMovies(@PageableDefault(sort = "popularity", direction = Sort.Direction.DESC) Pageable pageable, MovieFilterDto movieFilterDto) {
        return movieService.getMovies(pageable, movieFilterDto);
    }

    @PostMapping("/fetch/{tmdbId}")
    @ResponseStatus(HttpStatus.CREATED)
    public MovieOverviewDto fetchAndSaveMovie(@PathVariable Long tmdbId) {
        return movieService.fetchAndSaveMovie(tmdbId);
    }

    @PutMapping("/update/{id}")
    public MovieOverviewDto updateMovie(
            @PathVariable Long id,
            @Valid @RequestBody MovieRequestDto movieRequestDto
    ) {
        return movieService.updateMovie(id, movieRequestDto);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)  // Return 204 No Content
    public void deleteMovie(@PathVariable Long id) {
        movieService.deleteMovie(id);
    }

}
