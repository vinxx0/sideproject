package com.example.sideproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class AuthController {
    
    // 로그인 페이지
    @GetMapping("/login")
    public String loginForm() {
        return "auth/login";
    }
}
