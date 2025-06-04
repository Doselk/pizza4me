package de.hsos.gui;

import de.hsos.boundary.dto.PizzaDTO;
import de.hsos.control.BestellungenVerwalter;
import de.hsos.control.PizzenVerwalter;
import de.hsos.entity.Bestellung;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import io.quarkus.security.identity.SecurityIdentity;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.net.URI;
import java.util.List;

@Path("/pizzen/ui")
@Produces(MediaType.TEXT_HTML)
public class PizzaGuiResource {

    @Inject
    Template pizzenView;

    @Inject
    PizzenVerwalter pizzenVerwalter;

    @Inject
    BestellungenVerwalter bestellVerwalter;

   @Inject
    Template warenkorbView;

    @Inject
    SecurityIdentity securityIdentity;

    @GET
    @RolesAllowed("KundIn")
    public TemplateInstance zeigeAllePizzen() {
        List<PizzaDTO> pizzaDTOs = pizzenVerwalter.getAllePizzen()
                .stream()
                .map(PizzaDTO::toDTO)
                .toList();

        String userName = securityIdentity.getPrincipal().getName();  // aktueller Username;
        Bestellung bestellung = bestellVerwalter.getOderErstelleOffeneBestellung(userName);

        return pizzenView
                .data("pizzen", pizzaDTOs)
                .data("userName", userName)
                .data("bestellId", bestellung.getId());


    }

    @POST
    @Path("/pizza-hinzufuegen")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Transactional
    public Response pizzaZurBestellungHinzufuegen(@FormParam("bestellId") Long bestellId,
                                                  @FormParam("pizzaId") Long pizzaId,
                                                  @FormParam("menge") int menge) {
        bestellVerwalter.pizzaHinzufuegen(bestellId, menge, pizzaId);
        return Response.seeOther(URI.create("/pizzen/ui")).build();
    }

    @GET
    @Path("/warenkorb")
    @RolesAllowed("KundIn")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance zeigeWarenkorb() {
        String userEmail = securityIdentity.getPrincipal().getName();
        Bestellung bestellung = bestellVerwalter.getOffeneBestellungVon(userEmail);
        return warenkorbView.data("bestellung", bestellung);
    }
}