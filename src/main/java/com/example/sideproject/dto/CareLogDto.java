package com.example.sideproject.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.sideproject.common.CareType;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CareLogDto {
    
    @NotNull(message = "기록 유형을 선택해주세요")
    private CareType type; // WALK, FEED, POOP

    @NotNull(message = "날짜를 입력해주세요")
    private LocalDate date;
    private LocalTime time;
    private String memo;
    private List<MultipartFile> images; // 여러 장 업로드
}
