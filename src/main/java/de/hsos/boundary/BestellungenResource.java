package de.hsos.boundary;

import de.hsos.boundary.dto.BestellpostenDTO;
import de.hsos.boundary.dto.BestellungDTO;
import de.hsos.boundary.dto.PizzaDTO;
import de.hsos.control.BestellungenVerwalter;
import de.hsos.entity.Pizza;
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
    public Response getBestellungen() {
        Collection<BestellungDTO> bestellungen = bestellungenVerwalter.getAllBestellungen()
                .stream()
                .map(BestellungDTO::toDTO)
                .toList();
        return Response.ok(bestellungen).build();
    }

    @POST
    @Operation(summary = "Neue Bestellung anlegen")
    public Response createBestellung(BestellungDTO bestellung) {
        Long bestellId = bestellungenVerwalter.bestellungAnlegen(bestellung);
        return Response.status(Response.Status.CREATED).entity(bestellId).build();
    }

}
