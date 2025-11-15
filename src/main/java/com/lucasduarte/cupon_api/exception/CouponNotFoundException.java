package com.lucasduarte.cupon_api.exception;

public class CouponNotFoundException extends RuntimeException{

    public CouponNotFoundException(String message) {
        super(message);
    }
}
