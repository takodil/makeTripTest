package com.testtribe.e2etest.flows;

import com.testtribe.e2etest.drivers.DriverFactory;
import com.testtribe.e2etest.helper.ReportingHelper;
import com.testtribe.e2etest.models.GuestBO;
import com.testtribe.e2etest.models.SearchBO;
import com.testtribe.e2etest.pages.HomePage;
import com.testtribe.e2etest.readers.PropertyReader;
import io.qameta.allure.Step;

import java.io.IOException;

public class HomePageFlow {
    @Step("Open home page.")
    public static void launchHomepage() {
        HomePage homePage = new HomePage();
        homePage.launchHomepage();
        homePage.closeLoginPopUp();
        try {
            ReportingHelper.takeScreenShotToReport("launchHomepage", DriverFactory.getCurrentDriver());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Step("Entering search parameters.")
    public static void enterSearchParameters(SearchBO search) {
        HomePage homePage = new HomePage();
        homePage.enterCity(search.departureCity);
        homePage.enterDate(search.checkInDate);
        homePage.enterDate(search.checkOutDate);
        try {
            ReportingHelper.takeScreenShotToReport("enterSearchParameters", DriverFactory.getCurrentDriver());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Step("Entering guests parameters.")
    public static void enterProductsParameters(GuestBO product) {
        HomePage homePage = new HomePage();
        homePage.enterProduct(product);
        homePage.applyProduct();
        try {
            ReportingHelper.takeScreenShotToReport("enterProductsParameters", DriverFactory.getCurrentDriver());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Step("Performing search.")
    public static void performSearch() {
        HomePage homePage = new HomePage();
        homePage.performSearch();
        try {
            ReportingHelper.takeScreenShotToReport("performSearch", DriverFactory.getCurrentDriver());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Step("Click on hotel tab.")
    public static void openHotel() {
        HomePage homePage = new HomePage();
        homePage.openHotelTab();
        try {
            ReportingHelper.takeScreenShotToReport("openHotel", DriverFactory.getCurrentDriver());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Step("Select trip type..")
    public static void selectTripType(String tripType) {
        HomePage homePage = new HomePage();
        homePage.selectTripType(tripType);
    }
}
