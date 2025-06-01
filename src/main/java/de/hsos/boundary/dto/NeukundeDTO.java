package de.hsos.boundary.dto;

import jakarta.json.bind.annotation.JsonbCreator;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.Objects;

public record NeukundeDTO(
        @Schema(description = "Vorname der neu erstellten Person")
        String vorname,
        @Schema(description = "Nachname der neu erstellten Person")
        String nachname,
        @Schema(description = "Straße der neu erstellten Person")
        String strasse,
        @Schema(description = "PLZ der neu erstellten Person")
        String plz,
        @Schema(description = "Ort der neu erstellten Person")
        String ort,
        @Schema(description = "Hausnummer der neu erstellten Person")
        Integer hausnummer

) {

    @JsonbCreator
    public NeukundeDTO {
        Objects.requireNonNull(vorname);
        Objects.requireNonNull(nachname);
    }

    public boolean hatAdresse() {
        return strasse != null && plz != null && ort != null && hausnummer != null;
    }
}
