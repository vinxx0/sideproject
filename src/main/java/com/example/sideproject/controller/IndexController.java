package com.example.sideproject.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.sideproject.entity.Pet;
import com.example.sideproject.entity.Schedule;
import com.example.sideproject.service.PetService;
import com.example.sideproject.service.ScheduleService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class IndexController {

    private final PetService petService;
    private final ScheduleService scheduleService;

    @GetMapping("/")
    public String index(Model model) {
        // TODO: Security 붙이면 로그인한 유저 id로 교체
        Long userId = 1L;

        List<Pet> pets = petService.getPetsByUser(userId);
        model.addAttribute("pets", pets);

        // 다가오는 일정 전체 (모든 반려동물)
        List<Schedule> upcomingSchedules = new ArrayList<>();
        for (Pet pet : pets) {
            upcomingSchedules.addAll(scheduleService.getUpcomingSchedules(pet.getId()));
        }
        model.addAttribute("upcomingSchedules", upcomingSchedules);

        return "index";
    }
    
    
}
