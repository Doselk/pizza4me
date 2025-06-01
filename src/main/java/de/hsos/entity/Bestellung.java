package de.hsos.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Bestellung {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Kunde kunde;

    private Status status;

    @OneToMany(mappedBy = "bestellung", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Bestellposten> bestellposten = new ArrayList<>();

    private LocalDateTime bestelldatum;

    public Bestellung(Kunde kunde) {
        this.kunde = kunde;
        this.status = Status.IN_BEARBEITUNG;
    }

    public Bestellung() {
        // Standardkonstruktor für JPA
    }

    public Long getId() {
        return id;
    }

    public Kunde getKunde() {
        return kunde;
    }

    public Status getStatus() {
        return status;
    }

    public List<Bestellposten> getBestellposten() {
        return bestellposten;
    }

    public LocalDateTime getBestelldatum() {
        return bestelldatum;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void bestellen() {
        this.status = Status.BESTELLT;
        this.bestelldatum = LocalDateTime.now();
    }

    public void addBestellposten(Bestellposten bestellposten) {
        if (bestellposten != null) {
            this.bestellposten.add(bestellposten);
            bestellposten.setBestellung(this);
        }
    }

}
