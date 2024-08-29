package com.example.spring2.service;

import java.util.HashSet;
import java.util.List;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.spring2.dto.request.UserCreationRequest;
import com.example.spring2.dto.request.UserUpdateRequest;
import com.example.spring2.dto.response.UserResponse;
import com.example.spring2.entity.User;
import com.example.spring2.enums.Role;
import com.example.spring2.exception.AppException;
import com.example.spring2.exception.ErrorCode;
import com.example.spring2.mapper.UserMapper;
import com.example.spring2.repository.UserRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true )
@Slf4j
public class UserService {
    UserRepository userRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;

    public User createUser(UserCreationRequest request){
        if(userRepository.existsByUsername(request.getUsername()))
            throw new AppException(ErrorCode.USER_EXISTED);
        
        User user = userMapper.toUser(request);

        user.setPassword(passwordEncoder.encode(request.getPassword()));

        HashSet<String> roles = new HashSet<>();
        roles.add(Role.USER.name());

        user.setRoles(roles);

        return userRepository.save(user);
    }

    public UserResponse updateUser(String id, UserUpdateRequest request){
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException(("User not found")));

        userMapper.updateUser(user, request);

        return userMapper.toUserResponse(userRepository.save(user));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<UserResponse> getUsers(){

        var authentication = SecurityContextHolder.getContext().getAuthentication();

        log.info("Username : {}",authentication.getName());
        authentication.getAuthorities().forEach(t -> log.info(t.getAuthority()));

        return userRepository.findAll().stream().map(u -> userMapper.toUserResponse(u)).toList();
    }

    @PostAuthorize("returnObject.username == authentication.name")
    public UserResponse getUser(String id){
        return userMapper.toUserResponse(userRepository.findById(id)
        .orElseThrow(() -> new RuntimeException(("User not found"))));
    }

    public UserResponse getInfo(){
        var context = SecurityContextHolder.getContext();
        String username = context.getAuthentication().getName();
        log.info(username);
        User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        
        return userMapper.toUserResponse(user);
    }

    public void deleteUser(String id){
        userRepository.deleteById(id);

    }
}
