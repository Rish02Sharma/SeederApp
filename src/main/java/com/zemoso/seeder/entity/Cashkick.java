package com.zemoso.seeder.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

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
    private Users users;

    @OneToMany(mappedBy = "cashkick", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<UserContract> userContracts;

    private String name;

    @Enumerated(EnumType.STRING)
    private STATUS status;

    private double totalFinanced;

    private double totalOutstanding;

    private LocalDateTime endDate;

    private enum STATUS{
        PENDING, APPROVED, REJECTED
    }
}
