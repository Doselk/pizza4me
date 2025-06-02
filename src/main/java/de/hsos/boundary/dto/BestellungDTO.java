package de.hsos.boundary.dto;

import de.hsos.entity.Kunde;
import de.hsos.entity.Status;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

public record BestellungDTO(
    @Schema(description = "Einddeutige id der Bestellung")
    Long id,
    @Schema(description = "Kunde der die Bestellung erstellt hat")
    Kunde kunde,
    @Schema(description = "Status der Bestellung")
    Status status
) {

}
