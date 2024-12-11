package com.facturacion.plasticsdeharo.exceptions;

public class FacturaCreationException extends RuntimeException {
    public FacturaCreationException(String message, Exception e) {
        super(message);
    }
}


