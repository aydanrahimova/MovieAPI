package com.example.internintelligence_movieapidevelopment.controller;

import com.example.internintelligence_movieapidevelopment.dto.request.GenreRequestDto;
import com.example.internintelligence_movieapidevelopment.dto.response.GenreOverviewDto;
import com.example.internintelligence_movieapidevelopment.dto.response.GenreResponseDto;
import com.example.internintelligence_movieapidevelopment.service.GenreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/genre")
@RequiredArgsConstructor
public class GenreController {

    private final GenreService genreService;

    @GetMapping("/{id}")
    public GenreResponseDto getMoviesByGenre(
            @PathVariable Long id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return genreService.getByID(id, page, size);
    }

    @GetMapping
    public List<GenreOverviewDto> getAll() {
        return genreService.getAll();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public GenreResponseDto addGenre(@RequestBody @Valid GenreRequestDto requestDto) {
        return genreService.addGenre(requestDto);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteByID(@PathVariable Long id) {
        genreService.deleteByID(id);
    }
}
