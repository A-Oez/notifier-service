package org.aoez.pushover;


import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.logging.Level;
import java.util.logging.Logger;

@Path("/pushover")
public class PushoverController { //https://pushover.net/api

    @Inject
    PushoverService service;

    @POST
    @Tag(name = "Pushover Controller", description = "Endpoints zum Senden von Pushover-Benachrichtigungen")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(
            summary = "Send Notification",
            description = "Receives a PushoverNotification object, formats it as JSON, and sends the notification via the external Pushover service."
    )
    @APIResponse(
            responseCode = "200"
    )
    @APIResponse(
            responseCode = "500",
            description = "INTERNAL_SERVER_ERROR – Error while forwarding request to pushover."
    )
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
