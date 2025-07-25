package de.hsos.boundary;

import de.hsos.boundary.dto.NeuPizzaDTO;
import de.hsos.boundary.dto.PizzaDTO;
import de.hsos.boundary.dto.UpdatePizzaDTO;
import de.hsos.control.PizzenVerwalter;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;

import java.util.Collection;

@Path("pizzen")
@ApplicationScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Transactional
public class PizzenResource {
    @Inject
    PizzenVerwalter pizzenVerwalter;

    @GET
    @PermitAll
    @Operation(summary = "Alle Pizzen abrufen")
    public Response getAllPizzen(){
        Collection<PizzaDTO> pizzen = pizzenVerwalter.getAllePizzen()
                .stream()
                .map(PizzaDTO::toDTO)
                .toList();
        return Response.ok(pizzen).build();
    }

    @GET
    @Path("/{id}")
    @PermitAll
    @Operation(summary = "Pizza mit ID abrufen")
    public Response getPizzenById(@PathParam("id") Long id){
        PizzaDTO pizzaDTO = pizzenVerwalter.getPizzaById(id)
                .map(PizzaDTO::toDTO)
                .orElseThrow(() -> new NotFoundException("Pizza with ID " + id + " not found"));
        if(pizzaDTO == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(pizzaDTO).build();
    }

    @POST
    @RolesAllowed("Admin")
    @Operation(summary = "Neue Pizza anlegen")
    public Response createPizza(NeuPizzaDTO neuPizzaDTO){
        try {
            if(neuPizzaDTO.name() == null || neuPizzaDTO.name().isBlank() ||
                    neuPizzaDTO.beschreibung() == null || neuPizzaDTO.beschreibung().isBlank() ||
                    neuPizzaDTO.preis() == null || neuPizzaDTO.preis().signum() <= 0) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Ungültige Pizza-Daten: Name, Beschreibung und Preis müssen gültig sein.")
                        .build();
            }

            Long id = pizzenVerwalter.hinzufuegenPizza(
                    neuPizzaDTO.name(), neuPizzaDTO.beschreibung(), neuPizzaDTO.preis());

            return Response.status(Response.Status.CREATED).entity(id).build();

        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Fehler beim Erstellen der Pizza: " + e.getMessage())
                    .build();
        }
    }

    @PATCH
    @Path("/{id}")
    @RolesAllowed("Admin")
    @Operation(summary = "Pizza aktualisieren")
    public Response patchPizza(@PathParam("id") Long id, UpdatePizzaDTO updateDTO){
        try {
            boolean aktualisiert = pizzenVerwalter.aktualisierePizza(id, updateDTO);
            if (!aktualisiert) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Pizza mit ID " + id + " nicht gefunden.")
                        .build();
            }
            return Response.noContent().build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Fehler beim Aktualisieren der Pizza: " + e.getMessage())
                    .build();
        }
    }


    @DELETE
    @Path("/{id}")
    @RolesAllowed("Admin")
    @Operation(summary = "Pizza Löschen")
    public Response deletePizza(@PathParam("id") Long id){
        try {
            boolean entfernt = pizzenVerwalter.entfernenPizza(id);
            if(!entfernt){
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Keine Pizza mit ID " + id + " gefunden.")
                        .build();
            }
            return Response.status(Response.Status.NO_CONTENT).build();

        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Fehler beim Löschen der Pizza: " + e.getMessage())
                    .build();
        }
    }
}
