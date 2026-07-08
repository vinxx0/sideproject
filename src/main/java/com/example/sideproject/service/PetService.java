package com.example.sideproject.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.sideproject.dto.PetDto;
import com.example.sideproject.entity.Pet;
import com.example.sideproject.entity.User;
import com.example.sideproject.repository.PetRepository;
import com.example.sideproject.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PetService {

    private final PetRepository petRepository;
    private final UserRepository userRepository;

    // 업로드 경로는 나중에 application.properties 값 주입받아서 사용 
    private final String uploadDir = System.getProperty("user.home") + "/upload/pets/";

    // 반려동물 등록
    public void register(PetDto petDto, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다"));
       String profileImageUrl = saveProfileImage(petDto.getProfileImage());

       Pet pet = Pet.builder()
               .user(user)
                .name(petDto.getName())
                .species(petDto.getSpecies())
                .gender(petDto.getGender())
                .breed(petDto.getBreed())
                .birthDate(petDto.getBirthDate())
                .profileImageUrl(profileImageUrl)
                .description(petDto.getDescription()) // ← 추가!
                .build();

        petRepository.save(pet);
    }

    // 반려동물 수정
    public void update(Long petId, PetDto petDto) {
        Pet pet = petRepository.findById(petId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 반려동물입니다"));

        String profileImageUrl = petDto.getProfileImage() != null && !petDto.getProfileImage().isEmpty()
                ? saveProfileImage(petDto.getProfileImage())
                : pet.getProfileImageUrl();
        
        pet.update(petDto.getName(), petDto.getSpecies(), petDto.getGender(),
                   petDto.getBreed(), petDto.getBirthDate(), profileImageUrl,
                   petDto.getDescription()); // ← 추가!
        petRepository.save(pet);
    }

    // 반려동물 삭제
    public void delete(Long petId) {
        petRepository.deleteById(petId);
    }


    // 특정 유저의 반려동물 목록 조회
    public List<Pet> getPetsByUser(Long userId) {
        return petRepository.findByUserId(userId);
    }

    // 반려동물 상세 조회
    public Pet getPet(Long petId) {
        return petRepository.findById(petId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 반려동물입니다"));
    }

    // 프로필 사진 저장 (파일 1장만)
    private String saveProfileImage(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return null;
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

            return "/images/pets/" + savedFilename;
        } catch (IOException e) {
            throw new RuntimeException("파일 저장에 실패했습니다", e); 
        }
    }    
}
