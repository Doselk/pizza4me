package de.hsos.entity;

import de.hsos.boundary.dto.BestellpostenDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
public class Bestellposten {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int menge;

    @ManyToOne
    private Bestellung bestellung;

    @ManyToOne
    @JoinColumn(name = "pizza_id", nullable = false)
    private Pizza pizza;

    private BigDecimal einzelpreis;

    public Bestellposten() { }

    public Bestellposten(Pizza pizza, int menge, Bestellung bestellung) {
        this.pizza = pizza;
        this.menge = menge;
        this.bestellung = bestellung;
        this.einzelpreis = pizza.getPreis();
    }

    public Bestellposten(Pizza pizza, int menge) {
        this.pizza = pizza;
        this.menge = menge;
    }

    // Getter/Setter …

    public Long getId() {
        return id;
    }

    public int getMenge() {
        return menge;
    }

    public void setMenge(int menge) {
        this.menge = menge;
    }

    public Bestellung getBestellung() {
        return bestellung;
    }

    public void setBestellung(Bestellung bestellung) {
        this.bestellung = bestellung;
    }

    public Pizza getPizza() {
        return pizza;
    }

    public void setPizza(Pizza pizza) {
        this.pizza = pizza;
    }

    public BigDecimal getEinzelpreis() {
        return einzelpreis;
    }

    public void setEinzelpreis(BigDecimal einzelpreis) {
        this.einzelpreis = einzelpreis;
    }

    //toDTO
    public BestellpostenDTO toDTO() {
        return new BestellpostenDTO(
                id,
                pizza.toDTO(),
                menge
        );
    }

}