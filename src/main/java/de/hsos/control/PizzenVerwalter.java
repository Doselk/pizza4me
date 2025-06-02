package de.hsos.control;

import de.hsos.entity.Pizza;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface PizzenVerwalter {

    Long hinzufuegenPizza(String name, String beschreibung, BigDecimal preis);

    boolean entfernenPizza(Long id);

    void aktualisierenPizza(String name, String neueBeschreibung, BigDecimal neuerPreis);

    String getPizzaDetails(String name);

    List<Pizza> getAllePizzen();

    Optional<Pizza> getPizzaById(Long id);

}
