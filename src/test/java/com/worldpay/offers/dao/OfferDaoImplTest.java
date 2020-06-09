package com.worldpay.offers.dao;

import com.worldpay.offers.model.DateTimeHelper;
import com.worldpay.offers.model.Offer;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class OfferDaoImplTest {

    private static final String offerId = "SomeOfferId";
    @Mock
    private DateTimeHelper dateTimeHelper;
    @Mock
    private OfferStorageProvider offerStorageProvider;

    private OfferDao offerDao;
    private Map<String, Offer> mapOfOffers;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        mapOfOffers = new HashMap<>();
        when(offerStorageProvider.generateNewOfferMap()).thenReturn(mapOfOffers);

        offerDao = new OfferDaoImpl(offerStorageProvider);

    }

    @Test
    public void mapOfOffersIsReturnedWhenGetAllOffersIsCalled() {
       assertEquals(offerDao.getAllOffers(), mapOfOffers);
    }

    @Test
    public void mapOfOffersIsReturnedWdsadashenGetAllOffersIsCalled() {
        assertEquals(offerDao.getAllOffers(), mapOfOffers);
    }

    @Test
    public void offerIsRemovedWhenRemoveOfferIsCalledAndOfferExists() {
        addNewOfferToMap();
        assertTrue(offerDao.removeOffer(offerId));
    }

    @Test
    public void offerIsNotRemovedWhenRemoveOfferIsCalledAndOfferDoesNotExist() {
        assertFalse(offerDao.removeOffer(offerId));
    }

    @Test
    public void whenGetOfferIsCalledAndOfferExistsOfferIsReturned() {
        Offer newOffer = new Offer(
            "OfferName", dateTimeHelper.getCurrentDateTime(), dateTimeHelper.getCurrentDateTime(), "Description", offerId);
        mapOfOffers.put(offerId, newOffer);

        Optional<Offer> offer = Optional.of(newOffer);
        Optional<Offer> optionalOffer = offerDao.getOffer(offerId);

        assertEquals(offer.get(), optionalOffer.get());
    }

    @Test
    public void whenGetOfferIsCalledAndOfferDoesNotExistEmptyOptionalOfferIsReturned() {

        Optional<Offer> emptyOptional = Optional.empty();
        Optional<Offer> optionalOffer = offerDao.getOffer(offerId);

        assertEquals(emptyOptional, optionalOffer);
    }

    @Test
    public void isOffersEmptyReturnsTrueWhenNoOffersExist() {
        assertTrue(offerDao.isOffersEmpty());
    }

    @Test
    public void isOffersEmptyReturnsFalseWhenOffersExist() {
        addNewOfferToMap();

        assertFalse(offerDao.isOffersEmpty());
    }

    @Test
    public void offerExistsReturnsTrueWhenOfferWithSpecifiedIdExists() {
        addNewOfferToMap();

        assertTrue(offerDao.offerExists(offerId));
    }

    @Test
    public void offerExistsReturnsFalseWhenOfferWithSpecifiedIdDoesNotExist() {
        assertFalse(offerDao.offerExists(offerId));
    }

    private void addNewOfferToMap() {
        Offer offer = new Offer(
            "OfferName", dateTimeHelper.getCurrentDateTime(), dateTimeHelper.getCurrentDateTime(), "Description", offerId);
        mapOfOffers.put(offerId, offer);
    }

}