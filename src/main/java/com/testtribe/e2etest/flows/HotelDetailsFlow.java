package com.testtribe.e2etest.flows;

import com.testtribe.e2etest.drivers.DriverFactory;
import com.testtribe.e2etest.helper.ReportingHelper;
import com.testtribe.e2etest.models.BookingBO;
import com.testtribe.e2etest.models.GuestBO;
import com.testtribe.e2etest.pages.HotelDetailsPage;
import io.qameta.allure.Step;

import java.io.IOException;

public class HotelDetailsFlow {
    @Step("Selecting room combo.")
    public static void selectCombo(GuestBO product) {
        HotelDetailsPage hotelDetailsPage = new HotelDetailsPage();
        hotelDetailsPage.selectCombo(product);
        try {
            ReportingHelper.takeScreenShotToReport("selectCombo", DriverFactory.getCurrentDriver());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Step("Print recommended combo, if exists")
    public static void printRecommendedCombo() {
        HotelDetailsPage hotelDetailsPage = new HotelDetailsPage();
        hotelDetailsPage.printRecommendedIfExists();
    }
}
