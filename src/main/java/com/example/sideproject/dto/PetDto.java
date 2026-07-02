package com.example.sideproject.dto;

import java.time.LocalDate;

import org.springframework.web.multipart.MultipartFile;

import com.example.sideproject.common.Gender;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PetDto {
    
    @NotBlank(message = "이름을 입력해주세요")
    private String name;

    @NotBlank(message = "종을 입력해주세요")
    private String species;

    @NotNull(message = "성별을 선택해주세요")
    private Gender gender;
    
    private String breed;
    private LocalDate birthDate;

    @NotNull(message = "프로필 사진을 등록해주세요")
    private MultipartFile profileImage; 
}
