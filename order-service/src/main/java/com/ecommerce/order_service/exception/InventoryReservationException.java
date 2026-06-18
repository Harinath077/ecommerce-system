package com.ecommerce.order_service.exception;

public class InventoryReservationException extends RuntimeException{
    public InventoryReservationException( String message ){
        super(message);
    }
}
