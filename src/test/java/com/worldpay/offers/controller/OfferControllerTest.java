package com.worldpay.offers.controller;

import com.worldpay.offers.dao.OfferDao;
import com.worldpay.offers.model.Offer;
import com.worldpay.offers.service.OfferService;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

class OfferControllerTest {

    private OfferController offerController;


    private Map<String, Offer> mapOfOffers;
    @Mock
    private OfferService offerService;
    @Mock
    private OfferDao offerDao;
    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    //TODO Implement these tests!!

}