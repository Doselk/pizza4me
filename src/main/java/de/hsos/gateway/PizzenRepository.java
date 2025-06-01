package de.hsos.gateway;

import de.hsos.control.PizzenVerwalter;
import de.hsos.entity.Pizza;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
@Transactional
public class PizzenRepository implements PizzenVerwalter {

    @Inject
    EntityManager em;

    @Override
    public void hinzufuegenPizza(String name, String beschreibung, double preis) {
        Pizza pizza = new Pizza(name, beschreibung, preis);
        em.persist(pizza);
    }

    @Override
    public void entfernenPizza(String name) {
        Pizza pizza = em.createQuery("SELECT p FROM Pizza p WHERE p.name = :name", Pizza.class)
                        .setParameter("name", name)
                        .getSingleResult();
        if (pizza != null) {
            em.remove(pizza);
        }
    }

    @Override
    public void aktualisierenPizza(String name, String neueBeschreibung, double neuerPreis) {
        Pizza pizza = em.createQuery("SELECT p FROM Pizza p WHERE p.name = :name", Pizza.class)
                        .setParameter("name", name)
                        .getSingleResult();
        if (pizza != null) {
            pizza = new Pizza(name, neueBeschreibung, neuerPreis);
            em.merge(pizza);
        }
    }

    @Override
    public String getPizzaDetails(String name) {
        Pizza pizza = em.createQuery("SELECT p FROM Pizza p WHERE p.name = :name", Pizza.class)
                        .setParameter("name", name)
                        .getSingleResult();
        if (pizza != null) {
            return String.format("Name: %s, Beschreibung: %s, Preis: %.2f",
                                 pizza.getName(),
                                 pizza.getBeschreibung(),
                                 pizza.getPreis().doubleValue());
        }
        return "Pizza nicht gefunden";
    }

    @Override
    public List<Pizza> getAllePizzen() {
        return em.createQuery("SELECT p FROM Pizza p", Pizza.class).getResultList();
    }
}
