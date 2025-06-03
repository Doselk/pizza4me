package de.hsos.gateway;

import de.hsos.boundary.dto.BestellungDTO;
import de.hsos.boundary.dto.NeuPizzaDTO;
import de.hsos.control.BestellungenVerwalter;
import de.hsos.entity.Bestellposten;
import de.hsos.entity.Bestellung;
import de.hsos.entity.Kunde;
import de.hsos.entity.Pizza;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class BestellungenRepository implements BestellungenVerwalter {

    @Inject
    EntityManager em;

    @Override
    public Long bestellungAnlegen(Long kundenId) {
        Kunde kunde = em.find(Kunde.class, kundenId);
        if (kunde == null) {
            throw new IllegalArgumentException("Kunde nicht gefunden");
        }

        Bestellung bestellung = new Bestellung(kunde);
        em.persist(bestellung);
        return bestellung.getId();
    }


    @Override
    public void pizzaHinzufuegen(Long bestellId, int menge, Long pizzaId) {
        Bestellung bestellung = em.find(Bestellung.class, bestellId);
        Pizza pizza = em.find(Pizza.class, pizzaId);
        System.out.printf("Hinzufügen: Bestellung-ID=%d, Pizza-ID=%d, Menge=%d%n", bestellId, pizzaId, menge);
        System.out.println("Bestellung gefunden? " + (bestellung != null));
        System.out.println("Pizza gefunden? " + (pizza != null));

        if (bestellung != null) {
            Bestellposten neuerPosten = new Bestellposten(pizza, menge, bestellung);
            bestellung.addBestellposten(neuerPosten);
            em.persist(neuerPosten);
        } else {
            throw new IllegalArgumentException("Bestellung mit ID " + bestellId + " nicht gefunden.");
        }
    }

    @Override
    public Optional<Bestellung> getBestellungById(Long bestellId) {
        return Optional.ofNullable(em.find(Bestellung.class, bestellId));
    }

    @Override
    public Collection<Bestellung> getAllBestellungen() {
        return em.createQuery("select b from Bestellung b", Bestellung.class).getResultList();
    }
}
