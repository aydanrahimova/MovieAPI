package com.example.internintelligence_movieapidevelopment.dto.response;

import com.example.internintelligence_movieapidevelopment.enums.PersonRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonOverviewDto {
    private String fullName;
    private PersonRole role;
}
