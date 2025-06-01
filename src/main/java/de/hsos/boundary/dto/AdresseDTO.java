package de.hsos.boundary.dto;

import de.hsos.entity.Adresse;

public class AdresseDTO {
    private String strasse;
    private String plz;
    private String ort;
    private Integer hausnummer;

    public AdresseDTO() {}

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

    public String getStrasse() { return strasse; }
    public String getPlz() { return plz; }
    public String getOrt() { return ort; }
    public Integer getHausnummer() { return hausnummer; }
}

