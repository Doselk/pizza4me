package de.hsos.boundary;

import de.hsos.boundary.dto.*;
import de.hsos.control.BestellungenVerwalter;
import de.hsos.entity.Pizza;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;

import java.util.Collection;

@Path("bestellung")
@ApplicationScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Transactional
public class BestellungenResource {
    @Inject
    BestellungenVerwalter bestellungenVerwalter;

    @GET
    @Operation(summary = "Alle Bestellungen holen")
    @RolesAllowed("Admin")
    public Response getBestellungen() {
        Collection<BestellungDTO> bestellungen = bestellungenVerwalter.getAllBestellungen()
                .stream()
                .map(BestellungDTO::toDTO)
                .toList();
        return Response.ok(bestellungen).build();
    }

    @POST
    @RolesAllowed("KundIn")
    @Operation(summary = "Neue Bestellung anlegen")
    public Response createBestellung(NeueBestellungDTO dto) {
        Long bestellId = bestellungenVerwalter.bestellungAnlegen(dto.kundenId());
        return Response.status(Response.Status.CREATED).entity(bestellId).build();
    }

    @PATCH
    @Path("/{id}")
    @RolesAllowed("KundIn")
    @Operation(summary = "Füge eine Pizza einer Bestellung hinzu")
    public Response addPizza(@PathParam("id") Long id, NeuBestellpostenDTO dto) {
        bestellungenVerwalter.pizzaHinzufuegen(id, dto.menge(), dto.pizzaId());
        return Response.ok().build();
    }

}
