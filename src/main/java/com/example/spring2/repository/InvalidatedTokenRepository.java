package com.example.spring2.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.spring2.entity.InvalidatedToken;

public interface InvalidatedTokenRepository extends JpaRepository<InvalidatedToken,String>{

}
