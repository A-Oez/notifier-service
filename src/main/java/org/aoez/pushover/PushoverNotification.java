package org.aoez.pushover;

import org.aoez.EnvService;

public record PushoverNotification() {

    public record BasicMessage(String title, String message) {}

    public static RequestBody toRequestBody(BasicMessage message, EnvService envService) {
        return new RequestBody(
                message.title(),
                message.message(),
                envService.getApiKey(),
                envService.getUserKey()
        );
    }

    public record RequestBody(String title, String message, String token, String user) {}
}