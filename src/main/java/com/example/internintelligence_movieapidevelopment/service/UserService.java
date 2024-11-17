package com.example.internintelligence_movieapidevelopment.service;

import com.example.internintelligence_movieapidevelopment.dao.entity.User;
import com.example.internintelligence_movieapidevelopment.dao.entity.Watchlist;
import com.example.internintelligence_movieapidevelopment.dao.repository.UserRepository;
import com.example.internintelligence_movieapidevelopment.dto.request.ChangePasswordDto;
import com.example.internintelligence_movieapidevelopment.dto.request.UserRequestDto;
import com.example.internintelligence_movieapidevelopment.dto.response.UserResponseDto;
import com.example.internintelligence_movieapidevelopment.exception.AlreadyExistException;
import com.example.internintelligence_movieapidevelopment.exception.IllegalArgumentException;
import com.example.internintelligence_movieapidevelopment.exception.ResourceNotFound;
import com.example.internintelligence_movieapidevelopment.mapper.UserMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;

    public UserResponseDto addUser(UserRequestDto requestDto) {
        if (userRepository.existsByEmail(requestDto.getEmail())) {
            log.warn("User with {} email is exist", requestDto.getEmail());
            throw new AlreadyExistException("User with that email exists");
        }
        if (userRepository.existsByUserName(requestDto.getUserName())) {
            log.warn("User with {} username is exist", requestDto.getUserName());
            throw new AlreadyExistException("User with that user exists,try another one");
        }
        if(!requestDto.getPassword().equals(requestDto.getConfirmPassword())){
            log.warn("Password and confirm password don't match");
            throw new IllegalArgumentException("Illegal input for confirm password");
        }
        User user = userMapper.toEntity(requestDto);
        user.setWatchlist(new Watchlist());
        userRepository.save(user);
        log.info("User successfully added");
        return userMapper.toDto(user);
    }

    public UserResponseDto getUser(Long id){
        log.info("Attempting get user with {} ID",id);
        User user = userRepository.findById(id).orElseThrow(()->{
            log.warn("User with {} id is not found",id);
            return new ResourceNotFound("USER_NOT_FOUND");
        });
        return userMapper.toDto(user);
    }

    public List<UserResponseDto> getUsers() {
        log.info("Attempting get all users...");
        List<User> users = userRepository.findAll();
        log.info("All users returned.");
        return users.stream()
                .map(userMapper::toDto)
                .toList();
    }

    public void deleteUser(Long id){
        log.info("Attempting delete user ID '{}'",id);
        if(!userRepository.existsById(id)){
            log.info("Failed to delete: user ID '{}' doesn't exist",id);
            throw new ResourceNotFound("USER_NOT_FOUND");
        }
        userRepository.deleteById(id);
        log.info("User successfully deleted");
    }

    public void changePassword(Long id, ChangePasswordDto changePassword){
        log.info("Try to change a password for user {}",id);
        User user = userRepository.findById(id).orElseThrow(()->{
            log.warn("User with {} id doesn't exist",id);
            return new ResourceNotFound("USER_NOT_FOUND");
        });
        if(!user.getPassword().equals(changePassword.getCurrentPassword())){
            log.warn("Wrong password for user {} is entered",id);
            throw new IllegalArgumentException("WRONG_CURRENT_PASSWORD");
        }
        if(!changePassword.getNewPassword().equals(changePassword.getRetryPassword())){
            log.warn("New password doesn't match with retry password");
            throw new IllegalArgumentException("MISMATCHING");
        }
        log.info("User's password is successfully changed");
        user.setPassword(changePassword.getNewPassword());
    }




}
