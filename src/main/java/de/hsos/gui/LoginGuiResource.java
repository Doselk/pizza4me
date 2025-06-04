package de.hsos.gui;

import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

@Path("/login")
@Produces(MediaType.TEXT_HTML)
public class LoginGuiResource {

    @Inject
    Template login;

    @GET
    public TemplateInstance zeigeLogin(@QueryParam("error") String error) {
        return login.data("error", error != null);
    }
}

