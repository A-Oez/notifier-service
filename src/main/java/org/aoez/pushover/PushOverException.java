package org.aoez.pushover;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import org.aoez.GlobalExceptionMapper;

public class PushOverException extends WebApplicationException {
  public PushOverException(String message) {
    super(Response.status(Response.Status.BAD_REQUEST)
            .entity(new GlobalExceptionMapper.ClientErrResponse(Response.Status.BAD_REQUEST.getStatusCode(), message))
            .build());
  }

  public PushOverException(String message, Response.Status status) {
    super(Response.status(status)
            .entity(new GlobalExceptionMapper.ClientErrResponse(status.getStatusCode(), message))
            .build());
  }
}
