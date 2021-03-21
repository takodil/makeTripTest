package com.testtribe.e2etest.pages;

import com.testtribe.e2etest.models.BookingBO;
import com.testtribe.e2etest.models.GuestBO;
import com.testtribe.e2etest.models.SearchBO;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class SearchResultPage extends BasePage {
    private Logger logger = LogManager.getLogger(HomePage.class);
    By priceFilterDiv = By.xpath("//div[@class='input-range__slider']");
    By popularItemShowMore = By.id("hlistpg_proptypes_show_more");
    By allFilters = By.xpath("//div[contains(@class, 'filterWrap')]//div[contains(@class, 'filterRow')]");
    By filterSliderInputRangeDiv = By.xpath("//div[contains(@class, 'input-range')]");
    By appliedFilterList = By.xpath("//ul[@class='appliedFilters']//span[@class='latoBold']");
    By checkmarkCheckBox = By.className("checkmarkOuter");
    By searchedCityInput = By.xpath("//div[@id='hsw_inputBox_city']/input[@id='city']");
    By checkInInput = By.id("checkin");
    By checkOutInput = By.id("checkout");
    By guestInput = By.xpath("//input[@id='guest']");

    public void moveMinPriceFilterSlider(double minPrice) {
        retryUntilUpdatedVisible(10, popularItemShowMore);
        scrollToElement(popularItemShowMore);
        waitUntilElementClickable(priceFilterDiv);
        WebElement slider = driver.findElement(priceFilterDiv);
        int sliderLength = driver.findElement(filterSliderInputRangeDiv).getSize().getHeight();
        Actions move = new Actions(driver);
        for (int xOffset = 1; xOffset <= sliderLength; xOffset += 2) {
            move.dragAndDropBy(slider, xOffset, 0).build().perform();
            if (waitUntilAllElementVisible(appliedFilterList).size() > 0) {
                double selectedMinPrice = Double.parseDouble(driver.findElement(appliedFilterList).getText().substring(2).split("-")[0]);
                if (selectedMinPrice > minPrice) {
                    logger.info("Price filter reached the criteria.");
                    break;
                }

            }
        }
    }

    public void selectUserRating(String userRating) {
        retryUntilVisible(10, allFilters);
        List<WebElement> filters = waitUntilAllElementVisible(allFilters);
        List<WebElement> UserRatingList = null;
        for (WebElement element : filters) {
            scrollToElementByElement(element);
            if (element.getText().contains("User Rating")) {
                UserRatingList = waitUntilAllElementVisible(checkmarkCheckBox);
                for (WebElement el : UserRatingList) {
                    if (el.getText().contains(userRating)) {
                        scrollToElementByElement(el);
                        clickLinkButtonByElementWithJS(el);
                    }
                }
                break;
            }
        }
    }

    public void selectHotel(int i) {
        retryUntilClickable(20, By.id("Listing_hotel_" + i));
        clickLinkButton(By.id("Listing_hotel_" + i));
    }


    private int calculateDate(String valueFromPage) {
        String today = new SimpleDateFormat("EEE, d MMM yyyy").format(new Date());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE, d MMM yyyy");
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy", Locale.ENGLISH);
        String dateFromPage = LocalDate.parse(valueFromPage, formatter).format(formatter);

        Date firstDate = null;
        try {
            firstDate = sdf.parse(dateFromPage);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date secondDate = null;
        try {
            secondDate = sdf.parse(today);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long diffInMillies = Math.abs(secondDate.getTime() - firstDate.getTime());
        long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
        return (int) diff;
    }


    private Map<String, String> parseActualGuests(String value) {
        //1 Room, 1 Adult
        String extractedValue = value.replaceAll(",", "");
        //1 Room 1 Adult
        String[] extractedValueList = extractedValue.split(" ");
        Map<String, String> searchedGuest = new HashMap<>();
        for (int i = extractedValueList.length - 1; i > 0; i--) {
            searchedGuest.put(extractedValueList[i], extractedValueList[i - 1]);
        }
        return searchedGuest;
    }

    public BookingBO buildSearchParameters() {
        String actualCity = driver.findElement(searchedCityInput).getAttribute("value");
        if (actualCity.contains(",")) {
            int cityNameIndex = actualCity.indexOf(",");
            actualCity.substring(0, cityNameIndex - 1);
        }
        int actualCheckinDate = calculateDate(driver.findElement(checkInInput).getAttribute("value"));
        int actualCheckOutDate = calculateDate(driver.findElement(checkOutInput).getAttribute("value"));
        int actualRoom = Integer.valueOf(parseActualGuests(driver.findElement(guestInput).getAttribute("value")).get("Room"));
        int actualAdults = Integer.valueOf(parseActualGuests(driver.findElement(guestInput).getAttribute("value")).get("Adults"));
        int actualChildren = Integer.valueOf(parseActualGuests(driver.findElement(guestInput).getAttribute("value")).get("Children"));
        int[] actualChildrenArray = new int[actualChildren];


        SearchBO actualSearch = new SearchBO();
        actualSearch.setDepartureCity(actualCity);
        actualSearch.setCheckInDate(actualCheckinDate);
        actualSearch.setCheckOutDate(actualCheckOutDate);

        GuestBO actualGuest = new GuestBO();
        actualGuest.setAdultNum(actualAdults);
        actualGuest.setRoomNum(actualRoom);
        actualGuest.setChildrenAge(actualChildrenArray);

        BookingBO actualBooking = new BookingBO();
        actualBooking.setSearch(actualSearch);
        actualBooking.setGuest(actualGuest);
        return actualBooking;
    }
}
