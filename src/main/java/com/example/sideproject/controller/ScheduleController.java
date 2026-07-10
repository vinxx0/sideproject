package com.example.sideproject.controller;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.sideproject.common.ScheduleType;
import com.example.sideproject.dto.ScheduleDto;
import com.example.sideproject.entity.Schedule;
import com.example.sideproject.service.ScheduleService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/schedule")
public class ScheduleController {
    
    private final ScheduleService scheduleService;

    // 일정 목록 페이지
    @GetMapping("/list/{petId}")
    public String list(@PathVariable Long petId, Model model) {
    List<Schedule> schedules = scheduleService.getSchedulesByPet(petId);
    List<Schedule> upcoming = scheduleService.getUpcomingSchedules(petId);
    
    // D-day 계산
    Map<Long, Long> dDayMap = new HashMap<>();
    for (Schedule schedule : upcoming) {
        long dDay = java.time.temporal.ChronoUnit.DAYS.between(
            LocalDate.now(), schedule.getScheduledDate());
        dDayMap.put(schedule.getId(), dDay);
    }
    
    model.addAttribute("schedules", schedules);
    model.addAttribute("upcoming", upcoming);
    model.addAttribute("dDayMap", dDayMap);
    model.addAttribute("petId", petId);
    return "schedule/list";
}

    // 일정 등록 페이지
    @GetMapping("/register/{petId}")
    public String registerForm(@PathVariable Long petId, Model model) {
        model.addAttribute("scheduleDto", new ScheduleDto());
        model.addAttribute("petId", petId);
        model.addAttribute("scheduleTypes", ScheduleType.values());
        return "schedule/register";
    }

   // 일정 등록 처리
    @PostMapping("/register/{petId}")
    public String register(@PathVariable Long petId,
                        @Valid @ModelAttribute ScheduleDto scheduleDto,
                        BindingResult bindingResult,
                        Model model) {
    if (bindingResult.hasErrors()) {
        model.addAttribute("scheduleTypes", ScheduleType.values());
        model.addAttribute("petId", petId);
        return "schedule/register";
    }
        scheduleService.register(scheduleDto, petId);
        return "redirect:/schedule/list/" + petId;
}

    // 일정 수정 페이지
     @GetMapping("/{scheduleId}/edit")
     public String editForm(@PathVariable Long scheduleId, Model model) {
        Schedule schedule = scheduleService.getSchedule(scheduleId);
        model.addAttribute("schedule", schedule);
        model.addAttribute("scheduleTypes", ScheduleType.values());
        return "schedule/edit";
     }

   // 일정 수정 처리
    @PostMapping("/{scheduleId}/edit")
    public String edit(@PathVariable Long scheduleId,
                       @Valid @ModelAttribute ScheduleDto scheduleDto,
                       BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "schedule/edit";
        }

        scheduleService.update(scheduleId, scheduleDto);
        return "redirect:/schedule/list/" + scheduleService.getSchedule(scheduleId).getPet().getId();
    }

    // 일정 체크 완료
    @PostMapping("/{scheduleId}/complete")
    public String complete(@PathVariable Long scheduleId) {
        Long petId = scheduleService.getSchedule(scheduleId).getPet().getId();
        scheduleService.complete(scheduleId);
        return "redirect:/schedule/list/" + petId;
    }

    // 일정 삭제 
    @PostMapping("/{scheduleId}/delete")
    public String delete(@PathVariable Long scheduleId) {
        Long petId = scheduleService.getSchedule(scheduleId).getPet().getId();
        scheduleService.delete(scheduleId); // 빠진 코드 추가
        return "redirect:/schedule/list/" + petId;
    }
}



