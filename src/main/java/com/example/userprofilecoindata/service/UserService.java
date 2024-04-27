package com.example.userprofilecoindata.service;

import com.example.userprofilecoindata.entity.UserEntity;
import com.example.userprofilecoindata.mapper.UserMapper;
import com.example.userprofilecoindata.model.UserRequest;
import com.example.userprofilecoindata.model.UserResponse;
import com.example.userprofilecoindata.model.UserUpdateRequest;
import com.example.userprofilecoindata.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Autowired
    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public UserResponse signUp(UserRequest userRequest) {

        if (!isValidUsername(userRequest.getUserName()) || !isValidPassword(userRequest.getPassword())) {
            throw new IllegalArgumentException("Invalid username or password");
        }
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encryptedPassword = passwordEncoder.encode(userRequest.getPassword());
        userRequest.setPassword(encryptedPassword);

        UserEntity userEntity = userMapper.modelToEntity(userRequest);
        try {
            userRepository.save(userEntity);
        } catch (Exception e) {
            log.error("getMessage{}", e.getMessage());
            if (e.getMessage().contains(userRequest.getUserName())) {
                throw new IllegalArgumentException("Username already exists");
            }
            if (e.getMessage().contains(userRequest.getEmail())) {
                throw new IllegalArgumentException("Email already exists");
            } else {
                throw new RuntimeException("Unknown Exception");
            }
        }
        log.info("User signed up successfully: {}", userEntity.getUserName());
        UserResponse userResponse = userMapper.entityToModel(userEntity);

        return userResponse;
    }

    public void login(String username, String password) {
        UserEntity userEntity = userRepository.findByUserName(username);

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (userEntity == null || !passwordEncoder.matches(password, userEntity.getPassword())) {
            throw new IllegalArgumentException("Invalid username or password");
        }

        log.info("User logged in successfully: {}", username);
    }


    public void updateUser(Long userId, UserUpdateRequest userUpdateRequest) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        if(!isValidPassword(userUpdateRequest.getPassword()))
        {
            throw new IllegalArgumentException("Invalid Password");
        }
        userMapper.ToEntity(userUpdateRequest);
        try {
            userRepository.save(userEntity);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private boolean isValidUsername(String username) {
        return username.matches("^[a-zA-Z0-9]{4,15}$");
    }

    private boolean isValidPassword(String password) {
        return password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!])(?=\\S+$).{8,15}$");
    }
}
