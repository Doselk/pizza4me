package de.hsos.gui;

import de.hsos.boundary.dto.PizzaDTO;
import de.hsos.control.PizzenVerwalter;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import io.quarkus.security.identity.SecurityIdentity;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

@Path("/pizzen/ui")
@Produces(MediaType.TEXT_HTML)
public class PizzaGuiResource {

    @Inject
    Template pizzenView;

    @Inject
    PizzenVerwalter pizzenVerwalter;

    @Inject
    SecurityIdentity securityIdentity;

    @GET
    public TemplateInstance zeigeAllePizzen() {
        List<PizzaDTO> pizzaDTOs = pizzenVerwalter.getAllePizzen()
                .stream()
                .map(PizzaDTO::toDTO)
                .toList();

        String userName = securityIdentity.getPrincipal().getName();  // aktueller Username

        return pizzenView.data("pizzen", pizzaDTOs)
                .data("userName", userName);
    }
}