package org.aoez.pushover;


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
public class Controller {

    @Inject
    EnvService envService;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response post(BodyDTO body) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.pushover.net/1/messages.json"))
                .POST(HttpRequest.BodyPublishers.ofString(String.valueOf(body)))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return Response.ok().entity(response).build();
    }


}
