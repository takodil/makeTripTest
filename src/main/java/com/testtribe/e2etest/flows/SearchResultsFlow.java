package com.testtribe.e2etest.flows;

import com.testtribe.e2etest.drivers.DriverFactory;
import com.testtribe.e2etest.helper.ReportingHelper;
import com.testtribe.e2etest.models.BookingBO;
import com.testtribe.e2etest.pages.SearchResultPage;
import io.qameta.allure.Step;
import org.testng.Assert;

import java.io.IOException;


public class SearchResultsFlow {
    @Step("Applying price filter.")
    public static void applyFilter(double minPrice, String userRating) {
        SearchResultPage searchResultPage = new SearchResultPage();
        searchResultPage.moveMinPriceFilterSlider(minPrice);
        searchResultPage.selectUserRating(userRating);
        try {
            ReportingHelper.takeScreenShotToReport("applyFilter", DriverFactory.getCurrentDriver());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Step("Applying user rating filter.")
    public static void selectHotel() {
        SearchResultPage searchResultPage = new SearchResultPage();
        searchResultPage.selectHotel(5);
        try {
            ReportingHelper.takeScreenShotToReport("selectHotel", DriverFactory.getCurrentDriver());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Step("Asserting search parameters.")
    public static void assertSearchParameters(BookingBO expectedBooking) {
        SearchResultPage searchResultPage = new SearchResultPage();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        BookingBO actualBooking = searchResultPage.buildSearchParameters();
        Assert.assertTrue(actualBooking.search.departureCity.equals(expectedBooking.search.departureCity));
        Assert.assertEquals(actualBooking.search.checkInDate, expectedBooking.search.checkInDate);
        Assert.assertEquals(actualBooking.search.checkOutDate, expectedBooking.search.checkOutDate);
        Assert.assertEquals(actualBooking.product.childrenAge.length, expectedBooking.product.childrenAge.length);
        Assert.assertEquals(actualBooking.product.adultNum, expectedBooking.product.adultNum);
        Assert.assertEquals(actualBooking.product.roomNum, expectedBooking.product.roomNum);
    }
}
