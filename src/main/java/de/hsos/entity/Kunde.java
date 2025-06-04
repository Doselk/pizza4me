package de.hsos.entity;

import jakarta.enterprise.inject.Vetoed;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Vetoed
public class Kunde {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String vorname;
    private String nachname;
    private String email;

    @OneToOne(cascade = CascadeType.ALL)
    private Adresse adresse;

    @OneToMany(mappedBy = "kunde", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Bestellung> bestellungen = new ArrayList<>();

    public Kunde() {
    }

    public Kunde(String vorname, String nachname, String email) {
        this.vorname = vorname;
        this.nachname = nachname;
        this.email = email;
    }

    public Kunde(String vorname, String nachname, String email, Adresse adresse) {
        this.vorname = vorname;
        this.nachname = nachname;
        this.email = email;
        this.adresse = adresse;
    }

    public Long getId() {
        return id;
    }

    public String getVorname() {
        return vorname;
    }

    public String getNachname() {
        return nachname;
    }

    public void setAdresse(Adresse adresse) {
        this.adresse = adresse;
    }

    public Adresse getAdresse() {
        return adresse;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
