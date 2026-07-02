package com.example.sideproject.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.sideproject.entity.Schedule;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    // 특정 반려동물의 일정 조회
    List<Schedule> findByPetId(Long petId);

    List<Schedule> findByPetIdAndScheduledDateBetween(
            Long petId, LocalDate start, LocalDate end);
}
