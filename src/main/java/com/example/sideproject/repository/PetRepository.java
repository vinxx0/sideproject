package com.example.sideproject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.sideproject.entity.Pet;

public interface PetRepository extends JpaRepository<Pet, Long> {
    // 특정 유저의 반려동물 목록 조회
    List<Pet> findByUserId(Long userId);
    
}
