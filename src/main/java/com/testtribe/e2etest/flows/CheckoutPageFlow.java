package com.testtribe.e2etest.flows;

import com.testtribe.e2etest.models.BookingBO;
import com.testtribe.e2etest.pages.CheckoutPage;
import io.qameta.allure.Step;
import org.testng.Assert;

public class CheckoutPageFlow {
    @Step("Asserting booking data.")
    public static void assertBooking(BookingBO expectedBooking) {
        CheckoutPage checkoutPage = new CheckoutPage();
        checkoutPage.buildSearchParameters();
//        Assert.assertEquals(actualBooking.search.checkInDate, expectedBooking.search.checkInDate);
//        Assert.assertEquals(actualBooking.search.checkOutDate, expectedBooking.search.checkOutDate);
//        int totalActualGuests = actualBooking.guest.childrenAge.length + actualBooking.guest.adultNum;
//        int totalExpectedGuests = expectedBooking.guest.childrenAge.length + expectedBooking.guest.adultNum;
//        Assert.assertTrue(totalActualGuests > totalExpectedGuests);
    }
}
