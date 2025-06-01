package de.hsos.control;

import de.hsos.entity.Adresse;
import de.hsos.entity.Kunde;

import java.util.Collection;
import java.util.Optional;

public interface KundenVerwalter {
    long neuenKundenAnlegen(String vorname, String nachname, String strasse, String plz, String ort, Integer hausnummer);
    boolean kundeEntfernen(String id);
    Optional<Kunde> findeKundeMitId(long id);
    Collection<Kunde> findeAlleKunden();
    boolean adresseSetzen(long kundenId, Adresse adresse);


}
