package com.worldpay.offers.service;

import com.worldpay.offers.dao.OfferDao;
import com.worldpay.offers.exception.OfferAlreadyExistsException;
import com.worldpay.offers.exception.OfferNotFoundException;
import com.worldpay.offers.model.Offer;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.HashMap;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Test class for testing the functionality of the OfferService Class.
 */
public class OfferServiceTest {

    @InjectMocks
    private OfferService offerService;

    @Mock
    private OfferDao offerDaoMock;

    @Mock
    private Offer offerMock;

    @Mock
    private HashMap<String,Offer> mapOfOffers;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void addOfferIsCalledWhenOfferDoesNotAlreadyExist() {
        when(offerDaoMock.offerExists(anyString())).thenReturn(false);

        offerService.addOffer(offerMock);

        verify(offerDaoMock).addOffer(offerMock);
    }

    @Test(expected = OfferAlreadyExistsException.class)
    public void offerAlreadyExistsExceptionIsThrownWhenAddOfferIsCalledAndOfferAlreadyExists() {
        when(offerDaoMock.offerExists(anyString())).thenReturn(true);

        offerService.addOffer(offerMock);
    }

    @Test
    public void cancelOfferReturnsTrueWhenOfferExistsAndIsNotCancelledAndIsNotExpired() {
        Optional<Offer> optionalOffer = Optional.of(offerMock);
        when(offerDaoMock.getOffer(anyString())).thenReturn(optionalOffer);

        when(offerDaoMock.offerExists(anyString())).thenReturn(true);
        when(offerMock.isCancelled()).thenReturn(false);
        when(offerMock.isExpired()).thenReturn(false);

        assertTrue(offerService.cancelOffer(anyString()));
    }

    @Test
    public void cancelOfferReturnsFalseWhenOfferExistsAndIsCancelled() {
        Optional<Offer> optionalOffer = Optional.of(offerMock);
        when(offerDaoMock.getOffer(anyString())).thenReturn(optionalOffer);

        when(offerDaoMock.offerExists(anyString())).thenReturn(true);
        when(offerMock.isCancelled()).thenReturn(true);
        when(offerMock.isExpired()).thenReturn(false);

        assertFalse(offerService.cancelOffer(anyString()));
    }

    @Test
    public void cancelOfferReturnsFalseWhenOfferExistsAndIsExpired() {
        Optional<Offer> optionalOffer = Optional.of(offerMock);
        when(offerDaoMock.getOffer(anyString())).thenReturn(optionalOffer);

        when(offerDaoMock.offerExists(anyString())).thenReturn(true);
        when(offerMock.isCancelled()).thenReturn(false);
        when(offerMock.isExpired()).thenReturn(true);

        assertFalse(offerService.cancelOffer(anyString()));
    }


    @Test(expected = OfferNotFoundException.class)
    public void offerNotFoundExceptionIsThrownWhenCancelIsCalledAndOfferDoesNotExist() {
        Optional<Offer> optionalOffer = Optional.ofNullable(null);

        when(offerDaoMock.getOffer(anyString())).thenReturn(optionalOffer);
        offerService.cancelOffer(anyString());
    }


    @Test
    public void mapOfOffersIsReturnedWhenGetAllOffersIsCalledAndOffersExist() {
        when(offerDaoMock.isOffersEmpty()).thenReturn(false);
        assertEquals(offerService.getAllOffers(), mapOfOffers);
    }


    @Test
    public void emptyMapIsReturnedWhenGetAllOffersIsCalledAndNoOffersExist() {
        when(offerDaoMock.isOffersEmpty()).thenReturn(true);
        assertEquals(offerService.getAllOffers(), Collections.emptyMap());
    }


    @Test
    public void getOfferReturnsOfferWhenOfferExists() {
        Optional<Offer> optionalOffer = Optional.of(offerMock);
        when(offerDaoMock.getOffer(anyString())).thenReturn(optionalOffer);

        assertEquals(offerService.getOffer(anyString()), offerMock);
    }

    @Test(expected = OfferNotFoundException.class)
    public void offerNotFoundExceptionIsThrownWhenGetOfferIsCalledAndOfferDoesNotExists() {
        Optional<Offer> optionalOffer = Optional.ofNullable(null);
        when(offerDaoMock.getOffer(anyString())).thenReturn(optionalOffer);

        offerService.getOffer(anyString());
    }

}