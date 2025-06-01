package de.hsos.boundary;

import de.hsos.boundary.dto.AdresseDTO;
import de.hsos.boundary.dto.KundeDTO;
import de.hsos.boundary.dto.NeukundeDTO;
import de.hsos.control.KundenVerwalter;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;

import java.util.Collection;

@Path("kunden")
@ApplicationScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Transactional
public class KundenResource {
    @Inject
    KundenVerwalter kundenVerwalter;

    @GET
    @Operation(summary = "Alle Kunden abrufen")
    public Response getAllPersons() {
        Collection<KundeDTO> kunden = kundenVerwalter.findeAlleKunden()
                .stream()
                .map(KundeDTO::toDTO)
                .toList();
        return Response.ok(kunden).build();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Kunde mit ID abrufen")
    public Response getKundeById(@PathParam("id") Long id) {
        KundeDTO kundeDTO = kundenVerwalter.findeKundeMitId(id)
                .map(KundeDTO::toDTO)
                .orElseThrow(() -> new NotFoundException("Kunde mit ID " + id + " nicht gefunden"));
        if (kundeDTO == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(kundeDTO).build();
    }

    @POST
    @Operation(summary = "Neuen Kunden anlegen")
    public Response createKunde(NeukundeDTO neukundeDTO) {
        if ((neukundeDTO.strasse() != null && neukundeDTO.plz() == null) ||
                (neukundeDTO.plz() != null && neukundeDTO.ort() == null) ||
                (neukundeDTO.ort() != null && neukundeDTO.hausnummer() == null)) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Bitte entweder komplette Adresse angeben oder keine.").build();
        }

        long id = kundenVerwalter.neuenKundenAnlegen(neukundeDTO.vorname(), neukundeDTO.nachname(),
                neukundeDTO.strasse(), neukundeDTO.plz(), neukundeDTO.ort(), neukundeDTO.hausnummer());
        return Response.status(Response.Status.CREATED).entity(id).build();
    }






    @PUT
    @Path("/{id}/adresse")
    @Operation(summary = "Adresse für einen Kunden setzen oder ändern")
    public Response adresseSetzen(@PathParam("id") Long id, AdresseDTO adresseDTO) {
        boolean gesetzt = kundenVerwalter.adresseSetzen(id, adresseDTO.toAdresse());
        if (!gesetzt) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Kunde mit ID " + id + " nicht gefunden").build();
        }
        return Response.ok().build();
    }

}
