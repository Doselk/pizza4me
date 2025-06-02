package de.hsos.boundary.dto;

import de.hsos.entity.Bestellposten;
import de.hsos.entity.Bestellung;
import de.hsos.entity.Pizza;
import jakarta.json.bind.annotation.JsonbCreator;
import jakarta.json.bind.annotation.JsonbProperty;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.Objects;

public record BestellpostenDTO(
        @Schema(description = "Eindeutige ID des Bestellpostens.")
        Long id,
        @Schema(description = "Ausgewählte Pizza des Bestellpostens.")
        Pizza pizza,
        @Schema(description = "Menge der Pizza im Bestellpostens.")
        int menge,
        @Schema(description = "Zugehörige Bestellung des Bestellpostens.")
        Bestellung bestellung
) {
    @JsonbCreator
    public BestellpostenDTO{
        Objects.requireNonNull(id);
        Objects.requireNonNull(pizza);
        Objects.requireNonNull(bestellung);
    }

    public Bestellposten toBestellposten(){
        return new Bestellposten(pizza, menge, bestellung);
    }
}
