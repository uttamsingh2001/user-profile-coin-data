package com.example.userprofilecoindata.controller;

import com.example.userprofilecoindata.model.UserRequest;
import com.example.userprofilecoindata.model.UserResponse;
import com.example.userprofilecoindata.model.UserUpdateRequest;
import com.example.userprofilecoindata.service.CoinDataService;
import com.example.userprofilecoindata.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1")
public class UserController {
    private final UserService userService;
    private final CoinDataService coinDataService;
    @Autowired
    public UserController(UserService userService, CoinDataService coinDataService) {
        this.userService = userService;
        this.coinDataService = coinDataService;
    }

    @PostMapping("/user/signup")
    public ResponseEntity<UserResponse> signUp(@RequestBody UserRequest userRequest) {
        UserResponse userResponse;
        try {
            userResponse = userService.signUp(userRequest);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }

    @PostMapping("/user/login")
    public ResponseEntity<String> login(@RequestParam String username, @RequestParam String password) {
        try {
            userService.login(username, password);
        }  catch (IllegalArgumentException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("User Login Successfull", HttpStatus.OK);
    }

    @PutMapping("/user/{userId}/update")
    public ResponseEntity<String> updateUser(@PathVariable Long userId, @RequestBody UserUpdateRequest userUpdateRequest) {
        try {
            userService.updateUser(userId, userUpdateRequest);
        }  catch (IllegalArgumentException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("User updated Successfully", HttpStatus.ACCEPTED);
    }

    @GetMapping("/users/{userId}/coins/{symbol}")
    public ResponseEntity<String> getCoinData(@PathVariable Long userId, @PathVariable String symbol) {
        try {
            String responseData = coinDataService.fetchAndStoreCoinData(userId, symbol);
            return new ResponseEntity<>(responseData, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
