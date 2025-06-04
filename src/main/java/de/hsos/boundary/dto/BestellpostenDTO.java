package de.hsos.boundary.dto;

import de.hsos.entity.Bestellposten;
import de.hsos.entity.Bestellung;
import de.hsos.entity.Pizza;
import jakarta.json.bind.annotation.JsonbCreator;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.Objects;

public record BestellpostenDTO(
        @Schema(description = "Eindeutige ID des Bestellpostens.")
        Long id,
        @Schema(description = "Ausgewählte Pizza des Bestellpostens.")
        PizzaDTO pizza,
        @Schema(description = "Menge der Pizza im Bestellpostens.")
        int menge
) {
    @JsonbCreator
    public BestellpostenDTO{
        Objects.requireNonNull(id);
        Objects.requireNonNull(pizza);
    }

    public Bestellposten toBestellposten(){
        return new Bestellposten(pizza.toPizza(), menge);
    }

    public static BestellpostenDTO toDTO(Bestellposten posten) {
        return new BestellpostenDTO(
                posten.getId(),
                PizzaDTO.toDTO(posten.getPizza()),
                posten.getMenge()
        );
    }
}
