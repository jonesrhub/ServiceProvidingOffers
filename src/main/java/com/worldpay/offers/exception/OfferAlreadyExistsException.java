package com.worldpay.offers.exception;

public class OfferAlreadyExistsException extends RuntimeException {

    public OfferAlreadyExistsException(String offerId) {
        super("An offer with the id " + offerId + " already exists");
    }
}
