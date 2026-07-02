package com.example.sideproject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.sideproject.entity.CareLog;

public interface CareLogRepository extends JpaRepository<CareLog, Long> {
    // 특정 반려동물의 돌봄 기록 조회
    List<CareLog> findByPetId(Long petId);
    
}
