package com.worldpay.offers.service;

import com.worldpay.offers.dao.OfferDao;
import com.worldpay.offers.exception.OfferAlreadyExistsException;
import com.worldpay.offers.exception.OfferNotFoundException;
import com.worldpay.offers.model.Offer;

import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

/**
 * Service class which provides appropriate validation and error handling of data.
 */
@Service
public class OfferService {

    private final OfferDao offerDao;

    @Inject
    public OfferService(OfferDao offerDao) {
        this.offerDao = offerDao;
    }

    public void addOffer(Offer offer) {
        if(!offerExists(offer.getUniqueOfferId())) {
            offerDao.addOffer(offer);
        } else {
            throw new OfferAlreadyExistsException(offer.getUniqueOfferId());
        }
    }

    public boolean cancelOffer(String offerId) {
        Optional<Offer> existingOffer = Optional.ofNullable(getOffer(offerId));

        if(existingOffer.isPresent()) {
            if (!existingOffer.get().isCancelled()
                    && !existingOffer.get().isExpired()) {
                existingOffer.get().setCancelled(true);
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    public Map<String, Offer> getAllOffers() {
        if(isOffersEmpty()) {
            return Collections.emptyMap();
        } else {
            return offerDao.getAllOffers();
        }
    }

    public Offer getOffer(String offerId) {
        Optional<Offer> offer = offerDao.getOffer(offerId);

        if(offer.isPresent()) {
            return offer.get();
        } else {
            throw new OfferNotFoundException(offerId);
        }
    }

    private boolean isOffersEmpty() {
        return offerDao.isOffersEmpty();
    }


    //TODO Future work
    //    public Offer updateOffer(String offerId) {
//        if(offerExists(offerId)
//                && !isExpired(offerId)
//                    && !isCancelled(offerId)) {
//            return offerDao.addOffer(offer).get();
//        } else {
//            throw new OfferNotFoundException(offer.getUniqueOfferId());
//        }
//
//    }

    private boolean offerExists(String offerId) {
        return offerDao.offerExists(offerId);
    }
}
