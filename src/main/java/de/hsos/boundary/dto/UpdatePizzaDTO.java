package de.hsos.boundary.dto;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.math.BigDecimal;

public record UpdatePizzaDTO(
        @Schema(description = "Name der Pizza")
        String name,
        @Schema(description = "Preis der Pizza")
        BigDecimal preis,
        @Schema(description = "Beschreibung der Pizza")
        String beschreibung
) {}
