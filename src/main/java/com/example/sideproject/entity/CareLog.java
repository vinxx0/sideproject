package com.example.sideproject.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.hibernate.annotations.CreationTimestamp;

import com.example.sideproject.common.CareType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CareLog {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_id")
    private Pet pet;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CareType type; // WALK, FEED, POOP

    @Column(nullable = false)
    private LocalDate date;
    private LocalTime time;
    private String memo;
    private String imageUrl;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public void update(CareType type, LocalDate date,
                       LocalTime time, String memo, String imageUrl) {
        this.type = type;
        this.date = date;
        this.time = time;
        this.memo = memo;
        this.imageUrl = imageUrl;
    }
}
