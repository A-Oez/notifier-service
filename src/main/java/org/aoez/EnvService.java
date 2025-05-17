package org.aoez;

import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class EnvService {

    @ConfigProperty(name = "API_KEY")
    private String apiKey;

    @ConfigProperty(name = "USER_KEY")
    private String userKey;

    public String getApiKey() {
        return apiKey;
    }

    public String getUserKey() {
        return userKey;
    }
}
