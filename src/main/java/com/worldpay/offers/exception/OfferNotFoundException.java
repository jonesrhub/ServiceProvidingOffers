package com.worldpay.offers.exception;


public class OfferNotFoundException extends RuntimeException {

    public OfferNotFoundException(String offerId) {
        super("Offer with the id " + offerId + "does not exist");
    }
}
