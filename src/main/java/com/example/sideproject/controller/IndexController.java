package com.example.sideproject.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.sideproject.entity.Pet;
import com.example.sideproject.entity.Schedule;
import com.example.sideproject.security.UserDetailsImpl;
import com.example.sideproject.service.PetService;
import com.example.sideproject.service.ScheduleService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class IndexController {

    private final PetService petService;
    private final ScheduleService scheduleService;

  @GetMapping("/")
public String index(Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) {
    Long userId = userDetails.getUser().getId();

    List<Pet> pets = petService.getPetsByUser(userId);
    model.addAttribute("pets", pets);

    List<Schedule> upcomingSchedules = new ArrayList<>();
    for (Pet pet : pets) {
        upcomingSchedules.addAll(scheduleService.getUpcomingSchedules(pet.getId()));
    }
    model.addAttribute("upcomingSchedules", upcomingSchedules);

    Map<Long, Long> dDayMap = new HashMap<>();
    for (Schedule schedule : upcomingSchedules) {
        long dDay = java.time.temporal.ChronoUnit.DAYS.between(
            LocalDate.now(), schedule.getScheduledDate());
        dDayMap.put(schedule.getId(), dDay);
    }
    model.addAttribute("dDayMap", dDayMap);

    return "index";
}
    
    
}
