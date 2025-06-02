package de.hsos.boundary.dto;

import de.hsos.entity.Adresse;
import jakarta.json.bind.annotation.JsonbCreator;

public record AdresseDTO(
        String strasse,
        String plz,
        String ort,
        Integer hausnummer
) {
    @JsonbCreator
    public AdresseDTO(String strasse, String plz, String ort, Integer hausnummer) {
        this.strasse = strasse;
        this.plz = plz;
        this.ort = ort;
        this.hausnummer = hausnummer;
    }

    public static AdresseDTO from(Adresse adresse) {
        return new AdresseDTO(adresse.getStrasse(), adresse.getPlz(), adresse.getOrt(), adresse.getHausnummer());
    }

    public Adresse toAdresse() {
        return new Adresse(strasse, plz, ort, hausnummer);
    }
}

