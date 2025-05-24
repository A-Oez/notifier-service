package org.aoez.pushover;


import com.fasterxml.jackson.core.JsonProcessingException;
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

@Path("/api/pushover")
@Tag(name = "Pushover Notification", description = "Endpoint for dispatching Pushover messages. - https://pushover.net/api")
public class PushoverController {

    @Inject
    PushoverService service;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(
            summary = "Send Notification",
            description = "Receives a PushoverNotification object, formats it as JSON, and sends it to the external Pushover service."
    )
    @APIResponse(
            responseCode = "200",
            description = "OK – Notification was successfully sent."
    )
    @APIResponse(
            responseCode = "500",
            description = "INTERNAL_SERVER_ERROR – An error occurred while forwarding the request to the Pushover service."
    )
    public Response sendNotification(PushoverService.PushoverNotification body) throws IOException, InterruptedException {
        String jsonBody = service.createRequestJson(body);
        HttpResponse<String> response = service.executeRequest(jsonBody);

        return Response.status(response.statusCode())
                .entity(response.body())
                .build();
    }
}
