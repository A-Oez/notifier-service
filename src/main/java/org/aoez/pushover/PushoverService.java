package org.aoez.pushover;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.smallrye.common.constraint.NotNull;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.aoez.EnvService;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@ApplicationScoped
public class PushoverService {

    @Inject
    EnvService envService;

    @Inject
    ObjectMapper objectMapper;

    public String createRequestJson(PushoverNotification body) throws JsonProcessingException {
        return objectMapper.writeValueAsString(PushoverRequest.builder(envService)
                .title(body.title())
                .message(body.message())
                .device(body.device())
                .html(body.html())
                .priority(body.priority())
                .sound(body.sound())
                .timestamp(body.timestamp())
                .ttl(body.ttl())
                .url(body.url())
                .urlTitle(body.urlTitle())
                .attachmentBase64(body.attachmentBase64())
                .attachmentType(body.attachmentType())
                .build()
        );
    }

    public HttpResponse<String> executeRequest(String body) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.pushover.net/1/messages.json"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();

        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    @Schema(description = "A notification message to be sent via Pushover.")
    public record PushoverNotification(

            @NotNull
            @Schema(description = "Title of the notification.", example = "System Alert", required = true)
            String title,

            @NotNull
            @Schema(description = "Main message content delivered to the recipient.", example = "The server is down.", required = true)
            String message,

            @Schema(description = "Target device name. If set, the notification is sent to this specific device.", example = "Pixel-7", required = false)
            String device,

            @Schema(description = "Whether the message should be rendered as HTML. If false, plain text is used.", example = "true", required = false)
            Boolean html,

            @Schema(description = "Priority level (-2 to 2). Higher values mean more urgent messages. Defaults to 0.", example = "1", required = false)
            Integer priority,

            @Schema(description = "Name of the notification sound to be played on receipt.", example = "magic", required = false)
            String sound,

            @Schema(description = "UNIX timestamp indicating when the message was created or scheduled.", example = "1716531740", required = false)
            Long timestamp,

            @Schema(description = "Time-to-live in seconds. Defines how long the message is valid.", example = "3600", required = false)
            Integer ttl,

            @Schema(description = "A URL to be included in the message for reference.", example = "https://status.myapp.com", required = false)
            String url,

            @Schema(description = "Title for the embedded URL.", example = "View Status Page", required = false)
            String urlTitle,

            @Schema(description = "Base64-encoded content of an attachment (e.g., image or file).", required = false)
            String attachmentBase64,

            @Schema(description = "MIME type of the attachment (e.g., image/png, video/mp4).", example = "image/png", required = false)
            String attachmentType

    ) {}

}
