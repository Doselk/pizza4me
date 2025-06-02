package de.hsos.boundary.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import de.hsos.entity.Adresse;
import de.hsos.entity.Kunde;
import jakarta.json.bind.annotation.JsonbCreator;
import jakarta.json.bind.annotation.JsonbProperty;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

public record KundeDTO(
    @Schema(description = "Eindeutige ID des Kunden")
    Long id,
    @Schema(description = "Vorname des Kunden")
    String vorname,
    @Schema(description = "Nachname des Kunden")
    String nachname,
    @Schema(description = "Adresse des Kunden")
    AdresseDTO adresse
) {

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
}
