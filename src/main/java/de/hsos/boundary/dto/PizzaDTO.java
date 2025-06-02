package de.hsos.boundary.dto;

import de.hsos.entity.Pizza;
import jakarta.json.bind.annotation.JsonbCreator;
import jakarta.json.bind.annotation.JsonbProperty;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.math.BigDecimal;
import java.util.Objects;

public record PizzaDTO(
        @Schema(description = "Eindeutige ID der Pizza")
        long id,
        @Schema(description = "Name der Pizza")
        String name,
        @Schema(description = "Preis der Pizza")
        BigDecimal preis,
        @Schema(description = "Beschreibung der Pizza")
        String beschreibung
) {

    @JsonbCreator
    public PizzaDTO(
            long id,
            String name,
            BigDecimal preis,
            String beschreibung
    ) {
        this.id = id;
        this.name = Objects.requireNonNull(name);
        this.preis = Objects.requireNonNull(preis);
        this.beschreibung = Objects.requireNonNull(beschreibung);
    }

    public static PizzaDTO toDTO(Pizza pizza) {
        return new PizzaDTO(
                pizza.getId(),
                pizza.getName(),
                pizza.getPreis(),
                pizza.getBeschreibung()
        );
    }

    public Pizza toPizza() {
        return new Pizza(name, beschreibung, preis);
    }
}
