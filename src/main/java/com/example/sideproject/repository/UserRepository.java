package com.example.sideproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.sideproject.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{
    // 이메일로 유저 찾기 (로그인할 때 사용)
     User findByEmail(String email);
    
}
