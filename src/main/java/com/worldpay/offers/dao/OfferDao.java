package com.worldpay.offers.dao;

import com.worldpay.offers.model.Offer;


import java.util.Map;
import java.util.Optional;

/**
 * Interface defining what the Offer Data Accessor Object should implement.
 * Implemented by {@link OfferDaoImpl}
 */
public interface OfferDao {

    Optional<Offer> addOffer(Offer offer);

    boolean removeOffer(String offerId);

    Optional<Offer> getOffer(String offerId);

    boolean offerExists(String offerId);

    boolean isOffersEmpty();

    Map<String, Offer> getAllOffers();
}
