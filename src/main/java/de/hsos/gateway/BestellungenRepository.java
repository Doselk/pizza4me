package de.hsos.gateway;

import de.hsos.boundary.dto.BestellungDTO;
import de.hsos.boundary.dto.NeuPizzaDTO;
import de.hsos.control.BestellungenVerwalter;
import de.hsos.entity.Bestellposten;
import de.hsos.entity.Bestellung;
import de.hsos.entity.Pizza;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

public class BestellungenRepository implements BestellungenVerwalter {

    @Inject
    EntityManager em;

    @Override
    public Long bestellungAnlegen(BestellungDTO bestellung) {
        Bestellung neueBestellung = new Bestellung();
        em.persist(neueBestellung);
        return neueBestellung.getId();
    }

    @Override
    public void pizzaHinzufuegen(Long bestellId, int menge, Pizza pizza) {
        Bestellung bestellung = em.find(Bestellung.class, bestellId);
        if (bestellung != null) {
            Bestellposten neuerPosten = new Bestellposten(pizza, menge, bestellung);
            bestellung.addBestellposten(neuerPosten);
            em.persist(neuerPosten);
        } else {
            throw new IllegalArgumentException("Bestellung mit ID " + bestellId + " nicht gefunden.");
        }
    }
}
