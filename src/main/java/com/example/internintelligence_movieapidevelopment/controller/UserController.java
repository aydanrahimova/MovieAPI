package com.example.internintelligence_movieapidevelopment.controller;

import com.example.internintelligence_movieapidevelopment.dto.request.ChangePasswordDto;
import com.example.internintelligence_movieapidevelopment.dto.request.UserRequestDto;
import com.example.internintelligence_movieapidevelopment.dto.response.UserResponseDto;
import com.example.internintelligence_movieapidevelopment.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/{id}")
    public UserResponseDto getUserById(@PathVariable Long id){
        return userService.getUser(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponseDto addUser(@RequestBody UserRequestDto requestDto){
        return userService.addUser(requestDto);
    }

    @PatchMapping("/{id}")
    public void changePassword(@PathVariable Long id, @RequestBody ChangePasswordDto changePassword){
        userService.changePassword(id,changePassword);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id){
        userService.deleteUser(id);
    }


}
