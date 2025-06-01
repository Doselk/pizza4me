package de.hsos.entity;

import jakarta.enterprise.inject.Vetoed;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
@Vetoed
public class Adresse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String strasse;
    private String plz;
    private String ort;
    private int hausnummer;

    public Adresse() {
    }

    public Adresse(String strasse, String plz, String ort, int hausnummer) {
        this.strasse = strasse;
        this.plz = plz;
        this.ort = ort;
        this.hausnummer = hausnummer;
    }

    public Long getId() {
        return id;
    }

    public String getStrasse() {
        return strasse;
    }

    public void setStrasse(String strasse) {
        this.strasse = strasse;
    }

    public String getPlz() {
        return plz;
    }

    public String getOrt() {
        return ort;
    }

    public int getHausnummer() {
        return hausnummer;
    }




}
