package com.example.spring2.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
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
    ApiResponse<List<UserResponse>> getUsers(){
        return ApiResponse.<List<UserResponse>>builder()
                .result(userService.getUsers())
                .build();
    }

    @GetMapping("/{id}")
    UserResponse getUser(@PathVariable("id") String id){
        return userService.getUser(id);
    }

    @GetMapping("/my-info")
    public UserResponse getUserInfo(@AuthenticationPrincipal Jwt jwt) {
        String subject = jwt.getSubject();
        String userId = subject.split(":")[0];

        return userService.getUser(userId);
    }

    @GetMapping("/myInfo")
    public ApiResponse<UserResponse> getMyInfo() {
        return ApiResponse.<UserResponse>builder()
                .result(userService.getInfo())
                .build();
    }
    
    

    @PutMapping("/{id}")
    ApiResponse<UserResponse> updateUser(@PathVariable("id") String id,@RequestBody UserUpdateRequest request){
        return ApiResponse.<UserResponse>builder()
                .result(userService.updateUser(id,request))
                .build();
    }

    @DeleteMapping("/{id}")
    ApiResponse<String> deleteUser(@PathVariable("id") String id){
        userService.deleteUser(id);
        return ApiResponse.<String>builder()
                .result("User has been deleted")
                .build();
    }
}
