package com.example.internintelligence_movieapidevelopment.mapper;

import com.example.internintelligence_movieapidevelopment.dao.entity.Review;
import com.example.internintelligence_movieapidevelopment.dto.request.ReviewRequestDto;
import com.example.internintelligence_movieapidevelopment.dto.response.ReviewResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReviewMapper {
    Review toEntity(ReviewRequestDto requestDto);
    ReviewResponseDto toDto(Review review);
}
