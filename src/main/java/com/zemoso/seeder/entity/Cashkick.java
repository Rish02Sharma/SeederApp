package com.zemoso.seeder.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "cashkick")
@Getter
@Setter
public class Cashkick {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ElementCollection
    private List<Contract> contracts;

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
