package org.aoez;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.aoez.pushover.PushOverException;

import java.util.logging.Level;
import java.util.logging.Logger;

@Provider
public class GlobalExceptionMapper implements ExceptionMapper<Throwable> {

    @Override
    public Response toResponse(Throwable exception) {

        int statusCode = 500;

        if(exception instanceof PushOverException pushEx) {
            return pushEx.getResponse();
        }

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
        Logger.getLogger(GlobalExceptionMapper.class.getName())
                .log(Level.SEVERE, errorMessage, exception);
    }
}