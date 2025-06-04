package de.hsos.boundary.dto;

public record NeukundeDTO(String vorname, String nachname, String passwort,
                          String strasse, String plz, String ort, Integer hausnummer, String email) {
}
