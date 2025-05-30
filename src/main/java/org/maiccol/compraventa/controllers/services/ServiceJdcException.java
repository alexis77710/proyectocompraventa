package org.maiccol.compraventa.controllers.services;

public class ServiceJdcException extends RuntimeException{
    public ServiceJdcException(String message) {
        super(message);
    }
    public ServiceJdcException(String message, Throwable cause) {
        super(message, cause);
    }
}
