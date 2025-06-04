package de.hsos.gui;

import de.hsos.entity.Adresse;
import de.hsos.entity.Kunde;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import io.quarkus.qute.Location;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.net.URI;

@Path("/register")
@Produces(MediaType.TEXT_HTML)
public class RegisterResource {

    @Inject
    Template registerView;

    @Inject
    EntityManager em;

    // Zeigt das Registrierungsformular an
    @GET
    public TemplateInstance registerTemplate() {
        return registerView.data("fehler", null).data("nachricht", null);
    }
    // Verarbeitet das Registrierungsformular
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Transactional
    public Response submitRegistration(@FormParam("vorname") String vorname,
                                       @FormParam("nachname") String nachname,
                                       @FormParam("email") String email,
                                       @FormParam("strasse") String strasse,
                                       @FormParam("hausnummer") int hausnummer,
                                       @FormParam("plz") String plz,
                                       @FormParam("ort") String ort) {

        Adresse adresse = new Adresse(strasse, plz, ort, hausnummer);
        Kunde kunde = new Kunde(vorname, nachname, email, adresse);

        em.persist(adresse);
        em.persist(kunde);

        return Response.seeOther(URI.create("/login")).build();
    }
}
