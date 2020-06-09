package com.worldpay.offers.service;

import com.worldpay.offers.dao.OfferDao;
import com.worldpay.offers.model.DateTimeHelper;

import com.worldpay.offers.model.Offer;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.inject.Inject;


/**
 * Class to expire the items based on their expiry time.
 */
@Component
public class ExpireOffersTask {

    private static final Logger LOGGER = LogManager.getLogger(ExpireOffersTask.class);

    private final OfferDao offerDao;
    private final DateTimeHelper dateTimeHelper;

    @Inject
    public ExpireOffersTask(OfferDao offerDao, DateTimeHelper dateTimeHelper) {
        this.offerDao = offerDao;
        this.dateTimeHelper = dateTimeHelper;
    }

    /**
     * Method which is scheduled to run 'expireItemsTask' at the rate defined in application.properties.
     * If the list is empty then the task will not be executed
     */
    @Scheduled(fixedRateString = "${fixedrate.seconds}")
    public void executeExpireItemsTask() {
        if(!offerDao.isOffersEmpty()) {
            LOGGER.debug("List of active offers left in list: {}",
                offerDao.getAllOffers()
                    .entrySet().stream()
                    .filter(offer -> !offer.getValue().isExpired()).toString());

            for (Offer offer : offerDao.getAllOffers().values()) {
                if (shouldMarkAsExpired(offer)) {
                    LOGGER.info("The following offer was marked as expired {}", offer.toString());
                    offer.setExpired(true);
                }
            }
        }

    }

    /**
     * Checks whether the specified offer should be expired
     * @param offer
     * @return true if the offer is not cancelled, not expired and the current time is equal to the expiryTime
     */
    private boolean shouldMarkAsExpired(Offer offer) {
        return !offer.isCancelled()
            && !offer.isExpired()
            && dateTimeHelper.getCurrentDateTime().isEqual(offer.getExpiryTime());
    }


}
