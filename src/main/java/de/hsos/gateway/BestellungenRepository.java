package de.hsos.gateway;

import de.hsos.control.BestellungenVerwalter;
import de.hsos.control.KundenVerwalter;
import de.hsos.entity.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import java.util.Collection;
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
        System.out.println("Bestellung gefunden: " + (bestellung != null));
        System.out.println("Pizza gefunden: " + (pizza != null));

        if (bestellung != null) {
            Bestellposten neuerPosten = new Bestellposten(pizza, menge, bestellung);
            bestellung.addBestellposten(neuerPosten);
            em.persist(bestellung);
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

    @Override
    public Bestellung getOffeneBestellungVon(String email) {
        return em.createQuery(
                        "SELECT b FROM Bestellung b WHERE b.kunde.email = :email AND b.status = :status",
                        Bestellung.class
                )
                .setParameter("email", email)
                .setParameter("status", Status.IN_BEARBEITUNG)
                .getResultStream()
                .findFirst()
                .orElse(null);
    }

    @Inject
    KundenVerwalter kundenVerwalter;

    @Transactional
    public Bestellung getOderErstelleOffeneBestellung(String email) {
        Optional<Bestellung> b = Optional.ofNullable(this.getOffeneBestellungVon(email));
        if (b.isEmpty()) {
            Optional<Kunde> kunde = kundenVerwalter.findeKundeMitEmail(email);
            if (kunde.isEmpty()) {
                throw new IllegalStateException("Kein Kunde mit E-Mail " + email + " gefunden");
            }
            long kundenId = kunde.get().getId();
            long bestellId = this.bestellungAnlegen(kundenId);
            b = this.getBestellungById(bestellId);
        }
        return b.get();
    }

}
