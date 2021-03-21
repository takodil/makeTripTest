package com.testtribe.e2etest.pages;

import com.testtribe.e2etest.models.BookingBO;
import com.testtribe.e2etest.models.GuestBO;
import com.testtribe.e2etest.models.SearchBO;
import org.openqa.selenium.By;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class CheckoutPage extends BasePage{
    By checkInDateSpan = By.xpath("//div[contains(@class, 'chkCont')]//span[contains(text(), 'CHECK IN')]/following-sibling::span[contains(@class, 'appendBottom3')]");
    By checkOutDateSpan = By.xpath("//div[contains(@class, 'chkCont')]//span[contains(text(), 'CHECK OUT')]/following-sibling::span[contains(@class, 'appendBottom3')]");
    public void buildSearchParameters() {
//        int actualAdults = Integer.valueOf(parseActualGuests(driver.findElement(guestInput).getAttribute("value")).get("Adults"));
//        int actualChildren = Integer.valueOf(parseActualGuests(driver.findElement(guestInput).getAttribute("value")).get("Children"));
//        int[] actualChildrenArray = new int[actualChildren];
        retryUntilUpdatedVisible(30, checkInDateSpan);
        int actualCheckinDate = calculateDate(driver.findElement(checkInDateSpan).getText());
        int actualCheckOutDate = calculateDate(driver.findElement(checkOutDateSpan).getText());

//        SearchBO actualSearch = new SearchBO();
//        actualSearch.setCheckInDate(actualCheckinDate);
//        actualSearch.setCheckOutDate(actualCheckOutDate);
//
//        GuestBO actualGuest = new GuestBO();
//        actualGuest.setAdultNum(actualAdults);
//        actualGuest.setChildrenAge(actualChildrenArray);
//
//        BookingBO actualBooking = new BookingBO();
//        actualBooking.setSearch(actualSearch);
//        actualBooking.setGuest(actualGuest);
//        return actualBooking;
    }

    private int calculateDate(String valueFromPage) {
        String today = new SimpleDateFormat("dd MMM yyyy").format(new Date());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy");
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH);
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
}
