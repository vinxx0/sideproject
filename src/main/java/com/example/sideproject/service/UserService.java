package com.example.sideproject.service;

import com.example.sideproject.dto.UserDto;
import com.example.sideproject.entity.User;
import com.example.sideproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    // 회원가입
    public void register(UserDto userDto) {
        if (userRepository.findByEmail(userDto.getEmail()) != null) {
            throw new IllegalArgumentException("이미 가입된 이메일입니다");
        }

        User user = User.builder()
                .email(userDto.getEmail())
                .password(passwordEncoder.encode(userDto.getPassword())) // 암호화!
                .name(userDto.getName())
                .build();

        userRepository.save(user);
    }
}