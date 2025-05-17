package org.aoez;

import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class EnvService {

    @ConfigProperty(name = "API_KEY")
    String API_KEY;

    @ConfigProperty(name = "USER_KEY")
    String USER_KEY;

    public String getAPI() {
        return API_KEY;
    }

    public String getUSER() {
        return USER_KEY;
    }
}
