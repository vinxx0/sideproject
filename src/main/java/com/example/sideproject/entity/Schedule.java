package com.example.sideproject.entity;

import java.time.LocalDate;

import org.hibernate.annotations.ColumnDefault;

import com.example.sideproject.common.ScheduleType;

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
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_id")
    private Pet pet;
    @Column(nullable = false)
    private String title;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ScheduleType type; // VACCINE, CHECKUP, ETC
    @Column(nullable = false)
    private LocalDate scheduledDate;
    @ColumnDefault("false")
    private boolean repeating;
    @ColumnDefault("false")
    private boolean completed;
    public void complete() {
        this.completed = true;
    }

    public void update(String title, ScheduleType type,
                       LocalDate scheduledDate, boolean repeating) {
        this.title = title;
        this.type = type;
        this.scheduledDate = scheduledDate;
        this.repeating = repeating;
    }
}
