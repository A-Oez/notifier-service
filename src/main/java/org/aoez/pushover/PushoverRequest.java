package org.aoez.pushover;

import org.aoez.EnvService;


public class PushoverRequest {

    private final String title;
    private final String message;
    private final String token;
    private final String user;

    private PushoverRequest(Builder builder) {
        this.title = builder.title;
        this.message = builder.message;
        this.token = builder.envService.getApiKey();
        this.user = builder.envService.getUserKey();
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }

    public String getToken() {
        return token;
    }

    public String getUser() {
        return user;
    }

    public static Builder builder(EnvService envService) {
        return new Builder(envService);
    }

    public static class Builder {
        private String title;
        private String message;
        private final EnvService envService;

        private Builder(EnvService envService) {
            this.envService = envService;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public PushoverRequest build() {
            return new PushoverRequest(this);
        }
    }
}
