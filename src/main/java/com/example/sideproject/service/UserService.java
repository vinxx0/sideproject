package com.example.sideproject.service;

import org.springframework.stereotype.Service;

import com.example.sideproject.dto.UserDto;
import com.example.sideproject.entity.User;
import com.example.sideproject.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    // 회원가입
    public void register(UserDto userDto) {
        // 이메일 중복 체크
        if (userRepository.findByEmail(userDto.getEmail()) != null) {
            throw new IllegalArgumentException("이미 가입된 이메일입니다");
        }

        User user = User.builder()
                .email(userDto.getEmail())
                .password(userDto.getPassword()) // 암호화 나중에 적용
                .name(userDto.getName())
                .build();
        
        userRepository.save(user);
    }
}
