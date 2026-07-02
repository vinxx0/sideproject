package com.example.sideproject.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.sideproject.dto.CareLogDto;
import com.example.sideproject.entity.CareLog;
import com.example.sideproject.entity.Pet;
import com.example.sideproject.repository.CareLogRepository;
import com.example.sideproject.repository.PetRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CareLogService {
    
    private final CareLogRepository careLogRepository;
    private final PetRepository petRepository;

    private final String uploadDir = "C:/upload/carelogs/";
    private static final int MAX_IMAGE_COUNT = 5; // 사진 최대 5장
    private static final long MAX_FILE_SIZE = 10 * 1024 * 1024; // 장당 10MB

    // 돌봄 기록 등록
    public void register(CareLogDto careLogDto, Long petId) {
        Pet pet = petRepository.findById(petId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 반려동물 입니다"));

        List<String> imageUrls = saveImages(careLogDto.getImages());
    
        CareLog careLog = CareLog.builder()
                .pet(pet)
                .type(careLogDto.getType())
                .date(careLogDto.getDate())
                .time(careLogDto.getTime())
                .memo(careLogDto.getMemo())
                .imageUrl(String.join(",", imageUrls)) // 여러 장은 콤마로 구분해 저장
                .build();
        
        careLogRepository.save(careLog);
    }

    // 특정 반려동물의 돌봄 기록 조회
    public List<CareLog> getCareLogsByPet(Long petId) {
        return careLogRepository.findByPetId(petId);
    }

    // 여러 장 이미지 저장 + 수량/용량 검증
    private List<String> saveImages(List<MultipartFile> files) {
        List<String> imageUrls = new ArrayList<>();

        if(files == null || files.isEmpty()) {
            return imageUrls;
        }

        if (files.size() > MAX_IMAGE_COUNT) {
            throw new IllegalArgumentException("사진은 최대" + MAX_IMAGE_COUNT + "장까지 첨부할 수 있습니다");
        }

        for (MultipartFile file : files) {
            if (file.isEmpty()) continue;

            if (file.getSize() > MAX_FILE_SIZE) {
                throw new IllegalArgumentException(file.getOriginalFilename() + "파일이 10MB를 초과했습니다");
            }
            
            try {
                File dir = new File(uploadDir);
                if (!dir.exists()) {
                    dir.mkdirs();
                }

                String originalFilename = file.getOriginalFilename();
                String savedFilename = UUID.randomUUID() + "_" + originalFilename;
                Path savePath = Path.of(uploadDir + savedFilename);

                file.transferTo(savePath);

                imageUrls.add("/images/carelogs/" + savedFilename);
            } catch (IOException e) {
                throw new RuntimeException("파일 저장에 실패했습니다", e);
            }
        }

        return imageUrls;
    }
}
