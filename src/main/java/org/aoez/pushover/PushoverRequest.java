package org.aoez.pushover;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.aoez.EnvService;


public class PushoverRequest {
    @JsonProperty("title")
    private final String title;

    @JsonProperty("message")
    private final String message;

    @JsonProperty("token")
    private final String token;

    @JsonProperty("user")
    private final String user;

    @JsonProperty("device")
    private final String device;

    @JsonProperty("html")
    private final Boolean html;

    @JsonProperty("priority")
    private final Integer priority;

    @JsonProperty("sound")
    private final String sound;

    @JsonProperty("timestamp")
    private final Long timestamp;

    @JsonProperty("ttl")
    private final Integer ttl;

    @JsonProperty("url")
    private final String url;

    @JsonProperty("url_title")
    private final String urlTitle;

    @JsonProperty("attachment_base64")
    private final String attachmentBase64;

    @JsonProperty("attachment_type")
    private final String attachmentType;


    private PushoverRequest(Builder builder) {
        this.title = builder.title;
        this.message = builder.message;
        this.token = builder.envService.getApiKey();
        this.user = builder.envService.getUserKey();
        this.device = builder.device;
        this.html = builder.html;
        this.priority = builder.priority;
        this.sound = builder.sound;
        this.timestamp = builder.timestamp;
        this.ttl = builder.ttl;
        this.url = builder.url;
        this.urlTitle = builder.urlTitle;
        this.attachmentBase64 = builder.attachmentBase64;
        this.attachmentType = builder.attachmentType;
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

    public String getDevice() {
        return device;
    }

    public Boolean getHtml() {
        return html;
    }

    public Integer getPriority() {
        return priority;
    }

    public String getSound() {
        return sound;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public Integer getTtl() {
        return ttl;
    }

    public String getUrl() {
        return url;
    }

    public String getUrlTitle() {
        return urlTitle;
    }

    public String getAttachmentBase64() {
        return attachmentBase64;
    }

    public String getAttachmentType() {
        return attachmentType;
    }

    public static Builder builder(EnvService envService) {
        return new Builder(envService);
    }

    public static class Builder {
        private final EnvService envService;

        private String title;
        private String message;
        private String device;
        private Boolean html;
        private Integer priority;
        private String sound;
        private Long timestamp;
        private Integer ttl;
        private String url;
        private String urlTitle;
        private String attachmentBase64;
        private String attachmentType;

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

        public Builder device(String device) {
            this.device = device;
            return this;
        }

        public Builder html(Boolean html) {
            this.html = html;
            return this;
        }

        public Builder priority(Integer priority) {
            this.priority = priority;
            return this;
        }

        public Builder sound(String sound) {
            this.sound = sound;
            return this;
        }

        public Builder timestamp(Long timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public Builder ttl(Integer ttl) {
            this.ttl = ttl;
            return this;
        }

        public Builder url(String url) {
            this.url = url;
            return this;
        }

        public Builder urlTitle(String urlTitle) {
            this.urlTitle = urlTitle;
            return this;
        }

        public Builder attachmentBase64(String attachmentBase64) {
            this.attachmentBase64 = attachmentBase64;
            return this;
        }

        public Builder attachmentType(String attachmentType) {
            this.attachmentType = attachmentType;
            return this;
        }

        public PushoverRequest build() {
            return new PushoverRequest(this);
        }
    }
}

