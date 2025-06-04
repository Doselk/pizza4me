package de.hsos.entity;

import de.hsos.boundary.dto.PizzaDTO;
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

    public Pizza(String name, String beschreibung, BigDecimal preis) {
        this.name = name;
        this.beschreibung = beschreibung;
        this.preis = preis;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPreis() {
        return preis;
    }

    public void setPreis(BigDecimal preis) {
        this.preis = preis;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    //toDTO
    public PizzaDTO toDTO() {
        return new PizzaDTO(id, name, preis, beschreibung);
    }
}