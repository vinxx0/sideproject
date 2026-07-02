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

import com.example.sideproject.common.CareType;
import com.example.sideproject.dto.CareLogDto;
import com.example.sideproject.entity.CareLog;
import com.example.sideproject.service.CareLogService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/carelog")
public class CareLogController {
    
    private final CareLogService careLogService;

    // 돌봄 기록 목록 페이지
    @GetMapping("/list/{petId}")
    public String list(@PathVariable Long petId, Model model) {
        List<CareLog> careLogs = careLogService.getCareLogsByPet(petId);
        model.addAttribute("careLogs", careLogs);
        model.addAttribute("petId", petId);
        return "carelog/list";
    }

    // 돌봄 기록 등록 페이지
    @GetMapping("/register/{petId}")
    public String registerForm(@PathVariable Long petId, Model model) {
        model.addAttribute("careLogDto", new CareLogDto());
        model.addAttribute("petId", petId);
        model.addAttribute("careTypes", CareType.values());
        return "carelog/register";
    }

    // 돌봄 기록 등록 처리
    @PostMapping("/register/{petId}")
    public String register(@PathVariable Long petId,
                           @Valid @ModelAttribute CareLogDto careLogDto,
                           BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "carelog/register";
        }

        try {
            careLogService.register(careLogDto, petId);
        } catch (IllegalArgumentException e) {
            bindingResult.reject("error", e.getMessage());
            return "carelog/register";
        }

        return "redirect:/carelog/list" + petId;
    }

    // 돌봄 기록 수정 페이지
    @GetMapping("/{carelogId}/edit")
    public String editForm(@PathVariable Long careLogId, Model model) {
        CareLog careLog = careLogService.getCareLog(careLogId);
        model.addAttribute("careLog", careLog);
        model.addAttribute("careTypes", CareType.values());
        return "carelog/edit";
    }

    // 돌봄 기록 수정 처리
    @PostMapping("/{careLogId}/edit")
    public String edit(@PathVariable Long careLogId,
                       @Valid @ModelAttribute CareLogDto careLogDto,
                       BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "careog/edit";
        }

        careLogService.update(careLogId, careLogDto);
        return "redirect:/carelog/list/" + careLogService.getCareLog(careLogId).getPet().getId();
    }

    // 돌봄 기록 삭제
    @PostMapping("/{careLogId}/delete")
    public String delete(@PathVariable Long careLogId) {
        Long petId = careLogService.getCareLog(careLogId).getPet().getId();
        careLogService.delete(careLogId);
        return "redirect:/carelog/list/" + petId;
    }

}






