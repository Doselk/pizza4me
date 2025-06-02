package de.hsos.control;

import de.hsos.boundary.dto.UpdatePizzaDTO;
import de.hsos.entity.Pizza;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface PizzenVerwalter {

    Long hinzufuegenPizza(String name, String beschreibung, BigDecimal preis);

    boolean entfernenPizza(Long id);

    boolean aktualisierePizza(Long id, UpdatePizzaDTO dto);

    String getPizzaDetails(String name);

    List<Pizza> getAllePizzen();

    Optional<Pizza> getPizzaById(Long id);

}
