package com.ecommerce.order_service.exception;

import org.springframework.http.HttpStatus;

public class InventoryReservationException extends RuntimeException {
    private final HttpStatus status;

    public InventoryReservationException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
