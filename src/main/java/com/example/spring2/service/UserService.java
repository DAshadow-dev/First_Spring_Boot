package com.example.spring2.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.spring2.dto.request.UserCreationRequest;
import com.example.spring2.dto.request.UserUpdateRequest;
import com.example.spring2.entity.User;
import com.example.spring2.exception.AppException;
import com.example.spring2.exception.ErrorCode;
import com.example.spring2.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User createUser(UserCreationRequest request){
        User user = new User();

        if(userRepository.existsByUsername(request.getUsername()))
            throw new AppException(ErrorCode.USER_EXISTED);

        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setDob(request.getDob());

        return userRepository.save(user);
    }

    public User updateUser(String id, UserUpdateRequest request){
        User user = getUser(id);

        user.setPassword(request.getPassword());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setDob(request.getDob());

        return userRepository.save(user);
    }

    public List<User> getUsers(){
        return userRepository.findAll();
    }

    public User getUser(String id){
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException(("User not found")));
    }

    public void deleteUser(String id){
        userRepository.deleteById(id);
    }
}
