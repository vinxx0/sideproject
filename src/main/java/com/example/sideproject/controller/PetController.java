package com.example.sideproject.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.sideproject.dto.PetDto;
import com.example.sideproject.entity.Pet;
import com.example.sideproject.service.PetService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/pet")
public class PetController {
    
    private final PetService petService;

    // 반려동물 목록 페이지
    @GetMapping("/list")
    public String list(Model model) {
        // 보안에서 로그인한 유저의 id 가져올 예정
        Long userId = 1L; // 임시 유저 아이디
        List<Pet> pets = petService.getPetsByUser(userId);
        model.addAttribute("pets", pets);
        return "/pet/list";
    }

    // 반려동물 등록 페이지
    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("petDto", new PetDto());
        return "pet/register";
    }

    // 반려동물 등록 처리
    @PostMapping("/register")
    public String register(@Valid @ModelAttribute PetDto petDto,
                            BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            return "pet/register";
        }

        Long userId = 1L; // 임시 유저아이디
        petService.register(petDto, userId);
        return "redirect:/pet/list";
    }

    // 반려동물 상세 페이지
    @GetMapping("/{petId}")
    public String detail(@PathVariable Long petId, Model model) {
        Pet pet = petService.getPet(petId);
        model.addAttribute("pet", pet);
        return "pet/detail";
    }

    // 반려동물 수정 페이지
    @GetMapping("/{petId}/edit")
    public String editForm(@PathVariable Long petId, Model model) {
        Pet pet = petService.getPet(petId);
        model.addAttribute("pet", pet);
        return "pet/edit";
    }

    // 반려동물 수정 처리
    @PostMapping("/{petId}/edit")
    public String edit(@PathVariable Long petId, 
                       @Valid @ModelAttribute PetDto petDto,
                       BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "pet/edit";
        }

        petService.update(petId, petDto);
        return "redirect:/pet/" + petId;
    }

    // 반려동물 삭제
    @PostMapping("/{petId}/delete")
    public String delete(@PathVariable Long petId) {
        petService.delete(petId);
        return "redirect:/pet/list";
    }
}
