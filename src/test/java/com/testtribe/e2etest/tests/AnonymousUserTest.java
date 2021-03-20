package com.testtribe.e2etest.tests;

import com.testtribe.e2etest.dataproviders.Booking;
import com.testtribe.e2etest.flows.HomePageFlow;
import com.testtribe.e2etest.flows.HotelDetailsFlow;
import com.testtribe.e2etest.flows.SearchResultsFlow;
import com.testtribe.e2etest.models.BookingBO;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.annotations.Test;

public class AnonymousUserTest extends BaseTest{
    @Severity(SeverityLevel.BLOCKER)
    @Description("Testing booking flow as anonymous.")
    @Test(dataProvider = "validSearch", dataProviderClass = Booking.class, enabled = true)
    public void validRoundTripTest(BookingBO booking) {
        //TODO close login popup
        HomePageFlow.launchHomepage();
        HomePageFlow.openHotel();
        HomePageFlow.enterSearchParameters(booking.search);
        HomePageFlow.enterProductsParameters(booking.product);
        HomePageFlow.selectTripType(booking.type);
        HomePageFlow.performSearch();

        SearchResultsFlow.assertSearchParameters(booking);

        SearchResultsFlow.applyFilter(booking.minPrice, booking.userRating);
        SearchResultsFlow.selectHotel();

        HotelDetailsFlow.printRecommendedCombo();
        HotelDetailsFlow.selectCombo(booking.product);

    }
}
