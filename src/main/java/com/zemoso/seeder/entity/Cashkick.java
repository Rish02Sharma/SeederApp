package com.zemoso.seeder.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "cashkick")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Cashkick {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String name;

    @Enumerated(EnumType.STRING)
    private STATUS status;

    private double totalFinanced;

    private double totalRecieved;

    private double totalOutstanding;

    private LocalDate endDate;

    private LocalDate startDate;

    public enum STATUS{
        PENDING, APPROVED, REJECTED
    }
}
