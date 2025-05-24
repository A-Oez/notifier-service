package org.aoez.pushover;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

public class PushOverException extends WebApplicationException {
  public PushOverException(String message) {
    super(message, Response.Status.BAD_REQUEST);
  }

  public PushOverException(String message, Response.Status status) {
    super(message, status);
  }
}
