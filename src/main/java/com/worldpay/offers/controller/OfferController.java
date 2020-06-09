package com.worldpay.offers.controller;

import com.worldpay.offers.exception.OfferServiceException;
import com.worldpay.offers.model.DateTimeHelper;
import com.worldpay.offers.model.Offer;
import com.worldpay.offers.service.OfferService;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import javax.validation.Valid;
import java.util.Collections;
import java.util.Map;

/**
 * Controller class which defines all the endpoints for the application. Incoming requests are mapped
 * to the appropriate method to perform a specific action on the resource
 */
@RestController
@RequestMapping(
    consumes = {MediaType.APPLICATION_JSON_VALUE},
    produces = {MediaType.APPLICATION_JSON_VALUE}
    )
public class OfferController {

    private static final Logger LOGGER = LogManager.getLogger(OfferController.class);
    private final OfferService offersService;
    private final DateTimeHelper dateTimeHelper;

    @Inject
    public OfferController(OfferService offersService, DateTimeHelper dateTimeHelper) {
        this.offersService = offersService;
        this.dateTimeHelper = dateTimeHelper;
    }

    @GetMapping(path = "/offers")
    public ResponseEntity<Map<String, Offer>> showAllOffers() {
        LOGGER.info("Recieved call to get display all offers");

        if(!offersService.getAllOffers().isEmpty()) {
            return new ResponseEntity<>(offersService.getAllOffers(), getHttpHeaders(), HttpStatus.OK);
        }

        LOGGER.info("No offers to display");
        return new ResponseEntity<>(getHttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @GetMapping(path = "/offers/{id}")
    public ResponseEntity<Offer> getOffer(@Valid @PathVariable("id") String id) {
        LOGGER.info("Recieved call to get offer with id={}", id);

        Offer offer = offersService.getOffer(id);

        LOGGER.info("Successfully retrieved offer with id={}" , id);
        return new ResponseEntity<>(offer, getHttpHeaders(), HttpStatus.OK);
    }

    @PostMapping(path = "/offers")
    public ResponseEntity<Offer> createOffer(@Valid @RequestBody Offer offer) {
        String uniqueOfferId = offer.getUniqueOfferId();
        LOGGER.info("Recieved call to create offer with uniqueOfferId={}", uniqueOfferId);

        offer.setCreationTime(dateTimeHelper.getCurrentDateTime()); // can this be somewhere else?
        offersService.addOffer(offer);

        LOGGER.info("Created offer with id={}, name={}, expiryTime={}, description=\"{}\"",
            offer.getUniqueOfferId(),
            offer.getName(),
            offer.getExpiryTime(),
            offer.getDescription()
        );
        return new ResponseEntity<>(offer, getHttpHeaders(), HttpStatus.CREATED);

    }

    @DeleteMapping(path = "/offers/{id}")
    public ResponseEntity<Void> cancelOffer(@Valid @PathVariable String id) {
        LOGGER.info("Recieved call to cancel offer with id={}", id);

        if(offersService.cancelOffer(id)) {
            LOGGER.info("Successfully cancelled offer with id={}" , id);
            return new ResponseEntity<>(getHttpHeaders(), HttpStatus.NO_CONTENT); // report to the user that the delete was successful?
        }

        LOGGER.info("Failed to cancel offer with id={}" , id);
        throw new OfferServiceException("Unable to cancel offer with the id=" + id);
    }

    //TODO Future enhancements
//    @PatchMapping(path = "/offers/{id}")
//    public ResponseEntity<Offer> updateOffer(@PathVariable @Valid String id,
//                                             @RequestBody Map<String, String> jsonBody) {
//        String expiryTime = jsonBody.get("expiryTime");
//
//        LOGGER.info("Recieved call to update offer with id={}", id);
//        Offer updatedOffer = offersService.updateOffer(id);
//
//        LOGGER.info("Updated offer with id={}, name={}, expiryTime={}, description=\"{}\"",
//            id,
//            updatedOffer.getName(),
//            updatedOffer.getExpiryTime(),
//            updatedOffer.getDescription()
//        );
//
//        return new ResponseEntity<>(updatedOffer, getHttpHeaders(), HttpStatus.CREATED);
//
//    }

    private HttpHeaders getHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        return headers;
    }

}
