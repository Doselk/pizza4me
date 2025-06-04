package de.hsos.gui;

import de.hsos.boundary.KundenResource;
import de.hsos.boundary.dto.NeukundeDTO;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
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

    @Inject
    KundenResource kundenResource;

    // Zeigt das Registrierungsformular an
    @GET
    public TemplateInstance registerTemplate() {
        return registerView.data("fehler", null).data("nachricht", null);
    }

    @POST
    @Transactional
    public Response handleForm(
            @FormParam("vorname") String vorname,
            @FormParam("nachname") String nachname,
            @FormParam("email") String email,
            @FormParam("passwort") String passwort,
            @FormParam("strasse") String strasse,
            @FormParam("plz") String plz,
            @FormParam("ort") String ort,
            @FormParam("hausnummer") int hausnummer
    ) {
        NeukundeDTO dto = new NeukundeDTO(vorname, nachname, passwort, strasse, plz, ort, hausnummer, email);
        Response response = kundenResource.createKunde(dto);

        if (response.getStatus() == Response.Status.CREATED.getStatusCode()) {

            // Erfolgreich registriert → Weiterleitung zum Login
            return Response.seeOther(URI.create("/login")).build();
        }

        // Fehlerfall → Registrierungsseite erneut anzeigen mit Fehlermeldung
        return Response.status(Response.Status.BAD_REQUEST).entity(
                registerView.data("fehler", "Registrierung fehlgeschlagen").data("nachricht", null)
        ).build();
    }
}
