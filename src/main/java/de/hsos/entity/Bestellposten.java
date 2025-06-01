package de.hsos.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Bestellposten {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(optional = false)
    private Pizza pizza;

    private int menge;

    @ManyToOne
    private Bestellung bestellung;

    public Bestellposten(Pizza pizza, int menge, Bestellung bestellung) {
        this.pizza = pizza;
        this.menge = menge;
        this.bestellung = bestellung;
    }

    public Bestellposten() {
        // Standardkonstruktor für JPA
    }

    public Long getId() {
        return id;
    }

    public Pizza getPizza() {
        return pizza;
    }

    public int getMenge() {
        return menge;
    }

    public Bestellung getBestellung() {
        return bestellung;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPizza(Pizza pizza) {
        this.pizza = pizza;
    }

    public void setMenge(int menge) {
        this.menge = menge;
    }

    public void setBestellung(Bestellung bestellung) {
        this.bestellung = bestellung;
    }
}