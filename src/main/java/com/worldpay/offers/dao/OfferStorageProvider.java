package com.worldpay.offers.dao;

import com.worldpay.offers.model.Offer;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Provides a new SynchronizedMap for storage of the offers.
 * This is implemented in this way so that it can be mocked for unit testing
 * and provided to the DAO.
 */
@Component
public class OfferStorageProvider {

    public Map<String, Offer> generateNewOfferMap() {
        return Collections.synchronizedMap(new HashMap<>());
    }
}
