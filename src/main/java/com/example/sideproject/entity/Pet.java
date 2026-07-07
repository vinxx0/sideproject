package com.example.sideproject.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.example.sideproject.common.Gender;

import jakarta.persistence.CascadeType;
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
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String species; // 종 (강아지, 고양이 등)
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender; // 성별
    private String breed; // 품종 (말티푸, 러시안블루 등)
    private LocalDate birthDate;
    private String profileImageUrl;

    private String description; // 한 줄 소개

    @Builder.Default
    @OneToMany(
        mappedBy = "pet",
        fetch = FetchType.LAZY,
        cascade = CascadeType.ALL,
        orphanRemoval = true    
    )
    private List<CareLog> careLogs = new ArrayList<>();

    @Builder.Default
    @OneToMany(
        mappedBy = "pet",
        fetch = FetchType.LAZY,
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )

    private List<Schedule> schedules = new ArrayList<>();

    public void update(String name, String species, Gender gender,
                       String breed, LocalDate birthDate, String profileImageUrl, String description) {
        this.name = name;
        this.species = species;
        this.gender = gender;
        this.breed = breed;
        this.birthDate = birthDate;
        this.profileImageUrl = profileImageUrl;
        this.description = description;
    }
    
}





