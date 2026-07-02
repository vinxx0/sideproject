package com.example.sideproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.sideproject.dto.UserDto;
import com.example.sideproject.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    
    private final UserService userService;

    // 회원가입 페이지
    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("userDto", new UserDto());
        return "user/register";
    }

    // 회원가입 처리
    @PostMapping("/register")
    public String register(@Valid @ModelAttribute UserDto userDto,
                            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "user/register";
        }

        try {
            userService.register(userDto);
        } catch (IllegalArgumentException e) {
            bindingResult.rejectValue("email", "duplicate", e.getMessage());
            return "user/register";
        }

        return "redirect:/auth/login";
    }

    // 로그인 페이지
    @GetMapping("/login")
    public String loginForm() {
        return "auth/login";
    }
}
