package org.aoez;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.logging.Level;
import java.util.logging.Logger;

@Provider
public class GlobalThrowableMapper implements ExceptionMapper<Throwable> {

    private static final Logger LOGGER = Logger.getLogger(GlobalThrowableMapper.class.getName());


    @Override
    public Response toResponse(Throwable exception) {
        if (exception instanceof Error error) {
            LOGGER.log(Level.SEVERE, "Critical error: " + error.getClass().getSimpleName(), error);
            throw error;
        }

        int statusCode = 500;

        if (exception instanceof WebApplicationException webEx) {
            statusCode = webEx.getResponse().getStatus();
        }

        //internal logging
        log(exception);

        //exception to client
        return Response.status(statusCode)
                .entity(new ClientErrResponse(statusCode, exception.getMessage()))
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

    public record ClientErrResponse(int status, String message){}

    private void log(Throwable exception){
        String errorMessage = String.format(
                "Error: %s, Message: %s",
                exception.getClass().getSimpleName(),
                exception.getMessage()
        );

        LOGGER.log(Level.SEVERE, errorMessage, exception);
    }
}