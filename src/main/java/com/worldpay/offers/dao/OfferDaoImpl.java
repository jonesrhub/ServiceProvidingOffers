package com.worldpay.offers.dao;

import com.worldpay.offers.model.Offer;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;

/**
 * Implementation of {@link OfferDao}. Uses local storage to store Offers.
 */
@Repository
public class OfferDaoImpl implements OfferDao {

    private Map<String, Offer> mapOfOffers;

    public OfferDaoImpl(OfferStorageProvider offerStorageProvider) {
        mapOfOffers = offerStorageProvider.generateNewOfferMap();
    }

    @Override
    public Map<String, Offer> getAllOffers() {
        return mapOfOffers;
    }

    @Override
    public Optional<Offer> addOffer(Offer offer) {
       return Optional.ofNullable(mapOfOffers.put(offer.getUniqueOfferId(), offer));
    }

    @Override
    public boolean removeOffer(String offerId) {
        return mapOfOffers.keySet().removeIf(key -> key.equals(offerId));
    }

    @Override
    public Optional<Offer> getOffer(String offerId){
        return mapOfOffers.values().stream()
            .filter(offer -> offer.getUniqueOfferId().equals(offerId))
            .findAny();
    }

    @Override
    public boolean isOffersEmpty() {
        return mapOfOffers.isEmpty();
    }

    @Override
    public boolean offerExists(String offerId) {
        return !isOffersEmpty() && mapOfOffers.keySet().stream().
            anyMatch(key -> key.equals(offerId));
    }
}
