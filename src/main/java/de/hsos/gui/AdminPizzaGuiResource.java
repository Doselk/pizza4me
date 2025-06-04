package de.hsos.gui;

import de.hsos.boundary.dto.NeuPizzaDTO;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import de.hsos.control.PizzenVerwalter;
import de.hsos.boundary.dto.PizzaDTO;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@Path("/admin/pizzen")
@Produces(MediaType.TEXT_HTML)
@RolesAllowed("Admin")
public class AdminPizzaGuiResource {

    @Inject
    Template adminPizzaView;

    @Inject
    PizzenVerwalter pizzenVerwalter;

    @GET
    public TemplateInstance zeigeAllePizzen() {
        List<PizzaDTO> pizzaDTOs = pizzenVerwalter.getAllePizzen()
                .stream()
                .map(PizzaDTO::toDTO)
                .collect(Collectors.toList());

        return adminPizzaView.data("pizzen", pizzaDTOs);
    }

    @POST
    @Path("/neu-pizza-hinzufuegen")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Transactional
    public Response createPizza(@FormParam("name") String name,
                                @FormParam("beschreibung") String beschreibung,
                                @FormParam("preis") BigDecimal preis){
        try {
            if(name.isBlank() || beschreibung.isBlank() || preis.signum() <= 0) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Ungültige Pizza-Daten: Name, Beschreibung und Preis müssen gültig sein.")
                        .build();
            }
            Long id = pizzenVerwalter.hinzufuegenPizza(name, beschreibung, preis);
            return Response.seeOther(URI.create("/admin/pizzen")).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Fehler beim Erstellen der Pizza: " + e.getMessage())
                    .build();
        }
    }

    @POST
    @Path("/delete")
    @Transactional
    public Response loeschePizza(@FormParam("id") Long id) {
        try {
            boolean geloescht = pizzenVerwalter.entfernenPizza(id);
            if (!geloescht) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Pizza mit ID " + id + " nicht gefunden.")
                        .build();
            }
            return Response.seeOther(URI.create("/admin/pizzen")).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Fehler beim Löschen der Pizza: " + e.getMessage())
                    .build();
        }
    }
}
