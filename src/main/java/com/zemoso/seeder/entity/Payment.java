package com.zemoso.seeder.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "payment")
@Getter
@Setter
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users users;

    private LocalDateTime dueDate;

    private STATUS status;

    private double amount;

    private double outstanding;

    private enum STATUS{
        UPCOMING, PAID, MISSED
    }
}
