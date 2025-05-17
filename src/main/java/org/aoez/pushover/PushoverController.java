package org.aoez.pushover;


import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.logging.Level;
import java.util.logging.Logger;

@Path("/pushover")
public class PushoverController { //https://pushover.net/api

    @Inject
    PushoverService service;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response sendNotification(PushoverService.PushoverNotification body) {
        try {
            String jsonBody = service.createRequestJson(body);
            HttpResponse<String> response = service.executeRequest(jsonBody);

            return Response.status(response.statusCode())
                    .entity(response.body())
                    .build();
        } catch (IOException | InterruptedException e) {
            Logger.getLogger(PushoverController.class.getName())
                    .log(Level.SEVERE, "Error sending notification", e);

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error sending notification: " + e.getMessage())
                    .build();
        }
    }
}
