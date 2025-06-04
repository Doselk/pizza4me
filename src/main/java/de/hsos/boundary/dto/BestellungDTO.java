package de.hsos.boundary.dto;

import de.hsos.entity.Bestellposten;
import de.hsos.entity.Bestellung;
import de.hsos.entity.Kunde;
import de.hsos.entity.Status;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.List;

public record BestellungDTO(
    @Schema(description = "Einddeutige id der Bestellung")
    Long id,
    @Schema(description = "Kunde der die Bestellung erstellt hat")
    Kunde kunde,
    @Schema(description = "Status der Bestellung")
    Status status,
    @Schema(description = "Liste der Bestellposten in dieser Bestellung")
    List<BestellpostenDTO> bestellposten
) {
    public static BestellungDTO toDTO(Bestellung bestellung) {
        return new BestellungDTO(bestellung.getId(), bestellung.getKunde(), bestellung.getStatus(),
            bestellung.getBestellposten().stream()
                .map(Bestellposten::toDTO)
                .toList()
        );
    }

    public Bestellung toBestellung() {
        Bestellung bestellung = new Bestellung(kunde);
        bestellung.setId(id);
        bestellung.setStatus(status);
        bestellung.getBestellposten().addAll(
            bestellposten.stream()
                .map(BestellpostenDTO::toBestellposten)
                .toList()
        );
        return bestellung;
    }
}
