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
    private String beschreibung;

    public Pizza() {
    }

    public Pizza(String name, String beschreibung, double preis) {
        this.name = name;
        this.beschreibung = beschreibung;
        this.preis = BigDecimal.valueOf(preis);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPreis() {
        return preis;
    }

    public String getBeschreibung() {
        return beschreibung;
    }
}