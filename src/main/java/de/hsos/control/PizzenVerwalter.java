package de.hsos.control;

import de.hsos.entity.Pizza;

import java.util.List;

public interface PizzenVerwalter {

    void hinzufuegenPizza(String name, String beschreibung, double preis);

    void entfernenPizza(String name);

    void aktualisierenPizza(String name, String neueBeschreibung, double neuerPreis);

    String getPizzaDetails(String name);

    List<Pizza> getAllePizzen();

}
