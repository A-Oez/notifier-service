package org.aoez.pushover;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.aoez.EnvService;

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

    public record PushoverNotification(
            String title,

            // The actual message that is delivered to the recipient.
            String message,

            // Optional: The specific device to which the message should be sent.
            // Used when the recipient has multiple devices and you want to ensure targeted delivery.
            String device,

            // Optional: Flag indicating whether the message text should be interpreted as HTML (true) or as plain text (false).
            Boolean html,

            // Optional: The priority level of the message. Values typically range from -2 to 2,
            // where higher values indicate that the message is more urgent (e.g., emergency messages).
            Integer priority,

            // Optional: The name of a specific sound to be played when the message is received.
            String sound,

            // Optional: A UNIX timestamp indicating when the message was created or when it should be displayed.
            Long timestamp,

            // Optional: The "time to live" in seconds – indicates how long the message remains valid before it expires.
            Integer ttl,

            // Optional: A URL that can be embedded in the notification so that the recipient can access it.
            String url,

            // Optional: A title or description that is displayed with the URL.
            String urlTitle,

            // Optional: An attachment (e.g., an image or file). This is provided as a Base64-encoded string.
            String attachmentBase64,

            // Optional: The MIME type of the attachment, to specify, for example, whether it is an image, video, etc.
            String attachmentType
    ) {}

}
