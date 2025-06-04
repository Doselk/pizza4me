package de.hsos.control;

import de.hsos.boundary.dto.BestellungDTO;
import de.hsos.entity.Bestellung;
import de.hsos.entity.Pizza;

import java.util.Collection;
import java.util.Optional;

public interface BestellungenVerwalter {

    /**
     * Erstellt eine neue Bestellung.
     *
     * @param kundenId die zu erstellende Bestellung
     * @return die ID der neu erstellten Bestellung
     */
    Long bestellungAnlegen(Long kundenId);

    /**
     * Fügt eine neue Pizza zu einer bestehenden Bestellung hinzu.
     *
     * @param bestellId die ID der Bestellung, zu der die Pizza hinzugefügt werden soll
     * @param pizzaId die hinzuzufügende Pizza
     */
    void pizzaHinzufuegen(Long bestellId, int menge, Long pizzaId);

    Optional<Bestellung> getBestellungById(Long bestellId);
    Collection<Bestellung> getAllBestellungen();
    Bestellung getOffeneBestellungVon(String email);

    Bestellung getOderErstelleOffeneBestellung(String email);
}
