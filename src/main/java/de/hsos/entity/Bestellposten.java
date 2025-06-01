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
}