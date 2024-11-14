package com.zemoso.seeder.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "payment")
@Getter
@Setter
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private Long cashkickId;

    private LocalDate dueDate;

    @Enumerated(EnumType.STRING)
    private STATUS status;

    private double amount;

    private double outstanding;

    private LocalDate maturityDate;

    public enum STATUS{
        UPCOMING, PAID, MISSED
    }
}
