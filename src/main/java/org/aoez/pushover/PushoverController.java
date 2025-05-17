package org.aoez.pushover;


import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.aoez.EnvService;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Path("/pushover")
public class PushoverController {

    @Inject
    EnvService envService;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response sendNotification(PushoverNotification body) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String jsonBody = mapper.writeValueAsString(PushoverRequest.builder(envService)
                            .title(body.title())
                            .message(body.message())
                            .build());

            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://api.pushover.net/1/messages.json"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            return Response.status(response.statusCode())
                    .entity(response.body())
                    .build();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error sending notification: " + e.getMessage())
                    .build();
        }
    }

    private record PushoverNotification(
            String title,
            String message
    ) {}

}
