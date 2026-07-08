package com.example.sideproject.dto;

import java.time.LocalDate;

import com.example.sideproject.common.ScheduleType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScheduleDto {
    
    @NotBlank(message = "제목을 입력해주세요")
    private String title;

    @NotNull(message = "일정 유형을 선택해주세요")
    private ScheduleType type; // VACCINE, CHCKUP, ETC

    @NotNull(message = "날짜를 입력해주세요")
    private LocalDate schLocalDate;

    private boolean repeating; // 변경
}
