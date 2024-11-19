package com.zemoso.seeder.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "contract")
@Getter
@Setter
public class Contract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private STATUS status;

    private String type="MONTHLY";

    private double totalAvailable;

    private double rate;

    private double perPayment;

    private int termLength;

    public enum STATUS{
        AVAILABLE, UNAVAILABLE;
    }
}
