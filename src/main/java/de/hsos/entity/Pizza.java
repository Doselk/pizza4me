package de.hsos.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.math.BigDecimal;

@Entity
public class Pizza {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private BigDecimal preis;

}