package de.hsos.gateway;

import de.hsos.control.PizzenVerwalter;
import de.hsos.entity.Pizza;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import java.util.List;

public class PizzenRepository implements PizzenVerwalter {

    @Inject
    EntityManager em;

    @Override
    public void hinzufuegenPizza(String name, String beschreibung, double preis) {
        Pizza pizza = new Pizza(name, beschreibung, preis);
        em.persist(pizza);
        em.flush(); // Ensure the pizza is persisted immediately
    }

    @Override
    public void entfernenPizza(String name) {
        Pizza pizza = em.createQuery("SELECT p FROM Pizza p WHERE p.name = :name", Pizza.class)
                        .setParameter("name", name)
                        .getSingleResult();
        if (pizza != null) {
            em.remove(pizza);
        }
        // If no pizza is found, an exception will be thrown, which can be handled as needed
        em.flush(); // Ensure the removal is applied immediately

    }

    @Override
    public void aktualisierenPizza(String name, String neueBeschreibung, double neuerPreis) {

    }

    @Override
    public String getPizzaDetails(String name) {
        return "";
    }

    @Override
    public List<Pizza> getAllePizzen() {
        return em.createQuery("SELECT p FROM Pizza p", Pizza.class).getResultList();
    }
}
