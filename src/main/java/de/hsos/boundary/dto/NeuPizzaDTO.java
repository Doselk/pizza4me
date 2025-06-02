package de.hsos.boundary.dto;

import de.hsos.entity.Pizza;
import jakarta.json.bind.annotation.JsonbCreator;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.math.BigDecimal;
import java.util.Objects;

public record NeuPizzaDTO(
        @Schema(description = "Name der neu erstellten Pizza")
        String name,
        @Schema(description = "Preis der neu erstellten Pizza")
        BigDecimal preis,
        @Schema(description = "Beschreibung der neu erstellten Pizza")
        String beschreibung
) {

    @JsonbCreator
    public NeuPizzaDTO{
        Objects.requireNonNull(name);
        Objects.requireNonNull(preis);
        Objects.requireNonNull(beschreibung);
    }
}
