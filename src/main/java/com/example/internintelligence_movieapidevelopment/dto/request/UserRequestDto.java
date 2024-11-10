package com.example.internintelligence_movieapidevelopment.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDto {
    @NotBlank(message = "Full name is required")
    @Size(min = 2,max = 50,message = "The length of full name must be in range of 2 to 50")
    private String fullName;

    @NotBlank(message = "Username is required")
    @Size(min = 2,max = 50,message = "The length of username must be in range of 2 to 50")
    private String userName;

    @NotBlank(message = "Email is required")//email validation
    private String email;

    @NotBlank(message = "Password is required")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$")
    private String password;

    @NotBlank(message = "Confirmation for password is required")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$")
    private String confirmPassword;
}
