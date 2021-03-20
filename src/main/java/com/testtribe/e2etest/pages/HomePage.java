package com.testtribe.e2etest.pages;

import com.testtribe.e2etest.models.GuestBO;
import com.testtribe.e2etest.readers.PropertyReader;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class HomePage extends BasePage {
    private Logger logger = LogManager.getLogger(HomePage.class);
    private static final long TIME_OUT_IN_SECONDS = 10L;

    By cityButton = By.xpath("//div[contains(@class, 'selectHtlCity')]");
    By cityFieldInput = By.xpath("//div[contains(@class, 'hsw_autocomplePopu')]//input");
    By suggestionList = By.xpath("//ul[contains(@class, 'react-autosuggest__suggestions-list')]");
    By searchBtn = By.id("hsw_search_button");
    By loginPopUpDiv = By.xpath("//li[@data-cy='account']//div[contains(@class, 'autopop')]");
    By loginLinkDiv = By.xpath("//li[@data-cy='account']");
    By loginInput = By.id("username");
    By passwordInput = By.id("password");
    By continueButton = By.xpath("//button[@data-cy='continueBtn']/parent::div");
    By loginButton = By.xpath("//button[@data-cy='login']/parent::div");
    By hotelTabList = By.xpath("//li[@data-cy='menu_Hotels']");
    By guestDiv = By.xpath("//div[contains(@class, 'roomGuests')]");
    By adultCountUl = By.xpath("//ul[contains(@data-cy,'adultCount')]");
    By childrenCountUl = By.xpath("//p[@data-cy='childrenRange']");
    By applyProductButton = By.xpath("//button[@data-cy='submitGuest']");
    By openHotelDetailButton = By.xpath("//div[contains(@id, Listing_hotel_)]");
    By travellingReasonDiv = By.xpath("//div[contains(@class, 'travelFor')]");
    By travelForWorkList = By.xpath("//li[@data-cy='travelFor-Work']");
    By travelForLeisureList = By.xpath("//li[@data-cy='travelFor-Leisure']");

    public void launchHomepage() {
        logger.info("Opening homepage");
        openPage(PropertyReader.getProperty("base_url"));
    }

    public void enterCity(String city) {
        logger.info("Entering city " + city);
        clickLinkButton(cityButton);
        sendKeysTextField(cityFieldInput, city);
        waitForCitiesSuggestion(city);
        selectCityFromSuggestion(city);
    }

    public void enterProduct(GuestBO product) {
        logger.info("Entering product " + product);
        retryUntilClickable(20, guestDiv);
        clickLinkButton(guestDiv);
        clickLinkButtonByElement(driver.findElement(adultCountUl).findElement(By.xpath("//li[contains(@data-cy, 'adults-"+product.adultNum+"')]")));
        clickLinkButtonByElement(driver.findElement(childrenCountUl).findElement(By.xpath("//li[contains(@data-cy, 'children-"+product.childrenAge.length+"')]")));
        selectChildrenAge(product.childrenAge);
    }

    private void selectChildrenAge(int[] children) {
        logger.info("Selecting children age");
        for (int i = 0; i < children.length; i++){
            Select dropdown = new Select(driver.findElement(By.xpath("//select[@data-cy='childAge-" + i + "']")));
            dropdown.selectByVisibleText(String.valueOf(children[i]));
        }
    }

    private void selectCityFromSuggestion(String city) {
        logger.info("Selecting city from dropdown" + city);
        try {
            WebElement element = driver.findElement(suggestionList).findElement(By.xpath("//p[contains(text(), '" + city + "')]"));
            element.click();
        } catch (StaleElementReferenceException ex) {
            logger.warn("Elemet stale");
            WebElement element = driver.findElement(suggestionList).findElement(By.xpath("//p[contains(text(), '" + city + "')]"));
            element.click();
        }
    }

    private void waitForCitiesSuggestion(String city) {
        new WebDriverWait(driver, TIME_OUT_IN_SECONDS).until((ExpectedCondition<Boolean>) d -> {
            logger.info("Wait for suggestions to load.");
            try {
                return driver.findElement(suggestionList).findElement(By.xpath("//p[contains(text(), '" + city + "')]")).isDisplayed();
            } catch (StaleElementReferenceException e) {
                waitForCitiesSuggestion(city);
                logger.error("Stale element exception: {} occured." + e.getMessage());
            }
            return null;
        });
    }

    public void performSearch() {
        clickLinkButton(searchBtn);
        retryUntilClickable(30, openHotelDetailButton);
    }

    public void enterDate(int date) {
        String calculatedDay = calculateNextDate(date);
        logger.info("Entering check in & check out date. " + date + " day from today." );
        By calculatedDaySpan = By.xpath("//div[contains(@aria-label, ' " + calculatedDay + "')]");
        clickLinkButton(calculatedDaySpan);
    }

    private String calculateNextDate(int date) {
        String today = new SimpleDateFormat("MMM dd yyyy").format(new Date());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd yyyy");
        String desiredDate = LocalDate.parse(today, formatter).plusDays(date).format(formatter);
        return desiredDate;
    }
    public void closeLoginPopUp() {
        if (driver.findElements(loginPopUpDiv).size() > 0) {
            logger.info("Login pop up is displayed. Closing it.");
            Dimension window = driver.manage().window().getSize();
            new Actions(driver)
                    .moveByOffset(window.getHeight() / 2, window.getWidth() / 2)
                    .click()
                    .build()
                    .perform();
        }

    }

    public void openHotelTab() {
        clickLinkButton(hotelTabList);
    }

    public void applyProduct() {
        clickLinkButton(applyProductButton);
    }

    public void selectTripType(String tripType) {
        clickLinkButton(travellingReasonDiv);
        if (tripType.equalsIgnoreCase("work")){
            clickLinkButton(travelForWorkList);
        } else {
            clickLinkButton(travelForLeisureList);
        }
    }
}
