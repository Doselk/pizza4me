package de.hsos.boundary.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import de.hsos.entity.Adresse;
import de.hsos.entity.Kunde;
import jakarta.json.bind.annotation.JsonbCreator;
import jakarta.json.bind.annotation.JsonbProperty;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

public class KundeDTO {
    @Schema(description = "Eindeutige ID des Kunden")
    private Long id;
    @Schema(description = "Vorname des Kunden")
    private String vorname;
    @Schema(description = "Nachname des Kunden")
    private String nachname;
    @Schema(description = "Adresse des Kunden")
    private AdresseDTO adresse;

    public KundeDTO() {
    }

    @JsonbCreator
    public KundeDTO(Long id, String vorname, String nachname, AdresseDTO adresse) {
        this.id = id;
        this.vorname = vorname;
        this.nachname = nachname;
        this.adresse = adresse;
    }

    public static KundeDTO toDTO(Kunde kunde) {
        Adresse adresse = kunde.getAdresse();
        AdresseDTO adresseDTO = adresse != null ? AdresseDTO.from(adresse) : null;
        return new KundeDTO(kunde.getId(), kunde.getVorname(), kunde.getNachname(), adresseDTO);
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

    public AdresseDTO getAdresse() { return adresse; }
}
