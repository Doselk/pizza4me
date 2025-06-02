package de.hsos.gateway;

import de.hsos.control.PizzenVerwalter;
import de.hsos.entity.Pizza;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
@Transactional
public class PizzenRepository implements PizzenVerwalter {

    @Inject
    EntityManager em;

    @Override
    public Long hinzufuegenPizza(String name, String beschreibung, BigDecimal preis) {
        Pizza pizza = new Pizza(name, beschreibung, preis);
        em.persist(pizza);
        return pizza.getId();
    }

    @Override
    public boolean entfernenPizza(Long id) {
        Pizza pizza = em.createQuery("SELECT p FROM Pizza p WHERE p.id = id", Pizza.class)
                        .getSingleResult();
        if (pizza != null) {
            em.remove(pizza);
            return true;
        }
        return false;
    }

    @Override
    public void aktualisierenPizza(String name, String neueBeschreibung, BigDecimal neuerPreis) {
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

    @Override
    public Optional<Pizza> getPizzaById(Long id) {
        return Optional.ofNullable(em.find(Pizza.class, id));
    }

}
