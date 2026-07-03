package com.example.sideproject.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.sideproject.dto.ScheduleDto;
import com.example.sideproject.entity.Pet;
import com.example.sideproject.entity.Schedule;
import com.example.sideproject.repository.PetRepository;
import com.example.sideproject.repository.ScheduleRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    
    private final ScheduleRepository scheduleRepository;
    private final PetRepository petRepository;

    // 일정 등록
    public void register(ScheduleDto scheduleDto, Long petId) {
        Pet pet = petRepository.findById(petId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 반려동물입니다"));

        Schedule schedule = Schedule.builder()
                .pet(pet)
                .title(scheduleDto.getTitle())
                .type(scheduleDto.getType())
                .scheduledDate(scheduleDto.getSchLocalDate())
                .isRepeating(scheduleDto.isRepeating())
                .completed(false)
                .build();
        
        scheduleRepository.save(schedule);
    }

    // 일정 단건 조회
    public Schedule getSchedule(Long scheduleId) {
        return scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 일정입니다"));
    }

    // 일정 수정
    public void update(Long scheduleId, ScheduleDto scheduleDto) {
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 일정입니다"));
        schedule.update(scheduleDto.getTitle(), scheduleDto.getType(),
                        scheduleDto.getSchLocalDate(), scheduleDto.isRepeating());
        scheduleRepository.save(schedule);
    }

    // 일정 삭제
    public void delete(Long scheduleId) {
        scheduleRepository.deleteById(scheduleId);
    }

    // 특정 반려동물의 일정 조회
    public List<Schedule> getSchedulesByPet(Long petId) {
        return scheduleRepository.findByPetId(petId);
    }

    // 일정 완료 체크
    public void complete(Long scheduleId) {
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 일정입니다"));

        schedule.complete();
        scheduleRepository.save(schedule);
    }

   // 다가오는 일정 조회 (D-day용, 오늘 이후 30일 이내)
    public List<Schedule> getUpcomingSchedules(Long petId) {
        LocalDate today = LocalDate.now();
        LocalDate thirtyDaysLater = today.plusDays(30);
        return scheduleRepository
                .findByPetIdAndScheduledDateBetween(petId, today, thirtyDaysLater);
    }
}
