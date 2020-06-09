package com.worldpay.offers.service;

import com.worldpay.offers.dao.OfferDao;
import com.worldpay.offers.model.DateTimeHelper;
import com.worldpay.offers.model.Offer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

/**
 * Class to test the functionality of the ExpireOffersTask
 * TODO revisit
 */
public class ExpireOffersTaskTest {

    private static final String firstOfferId = "firstOfferId";
    private static final String secondOfferId = "secondOfferId";
    private static final String offerName = "offerName";

    @Mock
    private OfferDao offerDao;
    
    private DateTimeHelper dateTimeHelper;

    private Map<String, Offer> mapOfOffers;
    private ExpireOffersTask expireOffersTask;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        mapOfOffers = new HashMap<>();

        when(offerDao.getAllOffers()).thenReturn(mapOfOffers);
        when(offerDao.isOffersEmpty()).thenReturn(false);

        dateTimeHelper = new DateTimeHelper();
        expireOffersTask = new ExpireOffersTask(offerDao, dateTimeHelper);
    }


    @After
    public void tearDown() {
        mapOfOffers.clear();
    }

    @Test
    public void offerIsExpiredIfExpiryTimeHasBeenReached() {
        Offer offer = new Offer(
            offerName, dateTimeHelper.getCurrentDateTime(), dateTimeHelper.getCurrentDateTime() , "Description", firstOfferId);
        mapOfOffers.put(firstOfferId, offer);

        expireOffersTask.executeExpireItemsTask();

        assertTrue(offer.isExpired());
    }



    @Test
    public void multipleOffersAreExpiredIfTheExpiryTimeHasBeenReached() {
        Offer firstOffer = new Offer(
            offerName, dateTimeHelper.getCurrentDateTime(), dateTimeHelper.getCurrentDateTime(), "Description", firstOfferId);

        Offer secondOffer = new Offer(
            offerName, dateTimeHelper.getCurrentDateTime(), dateTimeHelper.getCurrentDateTime(), "Description", secondOfferId);

        mapOfOffers.put(firstOfferId, firstOffer);
        mapOfOffers.put(secondOfferId, secondOffer);

        expireOffersTask.executeExpireItemsTask();

        assertTrue(firstOffer.isExpired());
        assertTrue(secondOffer.isExpired());
    }


    @Test
    public void offerIsNotMarkedAsExpiredIfExpiryTimeHasNotBeenReached() {
        Offer secondOffer = new Offer(
            offerName, dateTimeHelper.getCurrentDateTime(), dateTimeHelper.getCurrentDateTime().plusSeconds(1), "Description", secondOfferId);

        mapOfOffers.put(secondOfferId, secondOffer);

        expireOffersTask.executeExpireItemsTask();

        assertFalse(secondOffer.isExpired());
    }


}