package com.example.internintelligence_movieapidevelopment.controller;

import com.example.internintelligence_movieapidevelopment.dto.request.ChangePasswordDto;
import com.example.internintelligence_movieapidevelopment.dto.request.UserRequestDto;
import com.example.internintelligence_movieapidevelopment.dto.response.UserResponseDto;
import com.example.internintelligence_movieapidevelopment.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/{id}")
    public UserResponseDto getUser(@PathVariable Long id) {
        return userService.getUser(id);
    }

    @GetMapping("/get-all")
    public List<UserResponseDto> getUsers(){
        return userService.getUsers();
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponseDto addUser(@Valid @RequestBody UserRequestDto requestDto) {
        return userService.addUser(requestDto);
    }

    @PatchMapping("/change-password/{id}")
    public void changePassword(@PathVariable Long id,@Valid @RequestBody ChangePasswordDto changePassword) {
        userService.changePassword(id, changePassword);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteByID(@PathVariable Long id) {
        userService.deleteUser(id);
    }

}
