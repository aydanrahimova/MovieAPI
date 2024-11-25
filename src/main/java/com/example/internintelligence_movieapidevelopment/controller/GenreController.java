package com.example.internintelligence_movieapidevelopment.controller;

import com.example.internintelligence_movieapidevelopment.dto.request.GenreRequestDto;
import com.example.internintelligence_movieapidevelopment.dto.response.GenreOverviewDto;
import com.example.internintelligence_movieapidevelopment.dto.response.GenreResponseDto;
import com.example.internintelligence_movieapidevelopment.service.GenreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/genre")
@RequiredArgsConstructor
public class GenreController {

    private final GenreService genreService;

    @PostMapping("addTmdb")
    public List<GenreOverviewDto> add(){
        return genreService.fetchAllGenres();
    }

    @GetMapping("/{id}")
    public GenreResponseDto getGenreByID(
            @PathVariable Long id,
            @PageableDefault Pageable pageable
    ) {
        return genreService.getByID(id,pageable);
    }

    @GetMapping("/get-all")
    public List<GenreOverviewDto> getAll() {
        return genreService.getAll();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/add")
    public GenreOverviewDto addGenre(@Valid @RequestBody GenreRequestDto requestDto) {
        return genreService.addGenre(requestDto);
    }

    @PutMapping("/update/{id}")
    public GenreOverviewDto editGenreByID(
            @PathVariable Long id,
            @Valid @RequestBody GenreRequestDto requestDto
    ) {
        return genreService.editGenre(id, requestDto);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/delete/{id}")
    public void deleteByID(@PathVariable Long id) {
        genreService.deleteByID(id);
    }
}
