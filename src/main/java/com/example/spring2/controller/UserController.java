package com.example.spring2.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.example.spring2.dto.request.ApiResponse;
import com.example.spring2.dto.request.UserCreationRequest;
import com.example.spring2.dto.request.UserUpdateRequest;
import com.example.spring2.dto.response.UserResponse;
import com.example.spring2.entity.User;
import com.example.spring2.service.UserService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    ApiResponse<User> createUser(@RequestBody @Valid UserCreationRequest request){
        ApiResponse<User> apiResponse = new ApiResponse<>();

        apiResponse.setResult(userService.createUser(request));;
        return apiResponse;
    }

    @GetMapping
    List<User> getUsers(){
        return userService.getUsers();
    }

    @GetMapping("/{id}")
    UserResponse getUser(@PathVariable("id") String id){
        return userService.getUser(id);
    }

    @PutMapping("/{id}")
    UserResponse updateUser(@PathVariable("id") String id,@RequestBody UserUpdateRequest request){
        return userService.updateUser(id,request);
    }

    @DeleteMapping("/{id}")
    String deleteUser(@PathVariable("id") String id){
        userService.deleteUser(id);
        return "User has been deleted";
    }
}
