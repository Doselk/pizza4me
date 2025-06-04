package de.hsos.boundary;

import de.hsos.boundary.dto.AdresseDTO;
import de.hsos.boundary.dto.KundeDTO;
import de.hsos.boundary.dto.NeukundeDTO;
import de.hsos.control.KundenVerwalter;
import de.hsos.util.UserPropertiesWriter;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;

import java.io.IOException;
import java.util.Collection;

@Path("kunden")
@ApplicationScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Transactional
public class KundenResource {
    @Inject
    KundenVerwalter kundenVerwalter;

    @POST
    @Path("/registrieren")
    @PermitAll
    public Response registrieren(KundeDTO dto) {
        // Registrierung durchführen
        return Response.ok().build();
    }

    @GET
    @Operation(summary = "Alle Kunden abrufen")
    @RolesAllowed("Admin")
    public Response getAllPersons() {
        Collection<KundeDTO> kunden = kundenVerwalter.findeAlleKunden()
                .stream()
                .map(KundeDTO::toDTO)
                .toList();
        return Response.ok(kunden).build();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed("Admin")
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
    @PermitAll
    public Response createKunde(NeukundeDTO neukundeDTO) {

        long kundenId = kundenVerwalter.neuenKundenAnlegen(
                neukundeDTO.vorname(),
                neukundeDTO.nachname(),
                neukundeDTO.passwort(),
                neukundeDTO.strasse(),
                neukundeDTO.plz(),
                neukundeDTO.ort(),
                neukundeDTO.hausnummer(),
                neukundeDTO.email()
        );
        if (kundenId <= 0) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Fehler beim Anlegen des Kunden. Bitte überprüfen Sie die Eingabedaten.")
                    .build();
        }
        return Response.status(Response.Status.CREATED)
                .entity("Benutzer '"  + neukundeDTO.email() + " mit der ID: " + kundenId + "' wurde registriert und als KundIn eingetragen.")
                .build();
    }


    @PUT
    @Path("/{id}/adresse")
    @RolesAllowed("Admin")
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
