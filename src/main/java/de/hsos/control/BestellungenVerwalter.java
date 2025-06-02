package de.hsos.control;

import de.hsos.boundary.dto.BestellungDTO;
import de.hsos.entity.Pizza;

public interface BestellungenVerwalter {

    /**
     * Erstellt eine neue Bestellung.
     *
     * @param bestellung die zu erstellende Bestellung
     * @return die ID der neu erstellten Bestellung
     */
    Long bestellungAnlegen(BestellungDTO bestellung);

    /**
     * Fügt eine neue Pizza zu einer bestehenden Bestellung hinzu.
     *
     * @param bestellId die ID der Bestellung, zu der die Pizza hinzugefügt werden soll
     * @param pizza die hinzuzufügende Pizza
     */
    void pizzaHinzufuegen(Long bestellId, int menge, Pizza pizza);
}
