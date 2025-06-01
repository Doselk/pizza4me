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

    @OneToMany(mappedBy = "bestellung", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Bestellposten> bestellposten = new ArrayList<>();

    private LocalDateTime bestelldatum;
}
