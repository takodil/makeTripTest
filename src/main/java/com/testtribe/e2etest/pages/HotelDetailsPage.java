package com.testtribe.e2etest.pages;

import com.testtribe.e2etest.models.GuestBO;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;

import java.util.*;

public class HotelDetailsPage extends BasePage{
    By selectedGuestSpan = By.xpath("//div[contains(@class, 'selectedRoom')]/following-sibling::div/span[1]");
    By selectedRoomGuestP = By.xpath("//div[contains(@class, 'selectedRoom')]/p[contains(@class, 'appendBottom10')]");
    By roomDiv = By.xpath("//div[contains(@class, 'multiRoomRow')]");
    By addRoomButtonFirst = By.xpath("//div[contains(@class, 'multiRoomRow')]//a[contains(@id, 'add_room')][1]");
    By confirmButton = By.xpath("//a[contains(@class, 'btnConfirmBooking')]");
    By guestValueList = By.xpath("//li[contains(@class, 'ddListItem')]/p[1]");
    By dropdownDiv = By.xpath("//input[@class='ddHeaderTitle']/parent::div");
    By recommendedComboDiv = By.xpath("//div[contains(@class, 'comboWrap')]");
    public void selectCombo(GuestBO product) {
        int maxAdults = product.adultNum;
        int maxChildren = product.childrenAge.length;
        retryUntilVisible(20, roomDiv);
        scrollUntilElement(roomDiv);
        clickLinkButton(roomDiv);
        List<WebElement> allSelectedGuests;
        int selectedAdults = 0;
        int selectedChildren = 0;
        while (((maxAdults >= selectedAdults) && (maxChildren >= selectedChildren))){
            clickLinkButton(addRoomButtonFirst);
            selectedAdults = 0;
            selectedChildren = 0;
            allSelectedGuests = waitGetElements(selectedRoomGuestP);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (WebElement selectedRoom : allSelectedGuests){
                if(selectedRoom.getText().contains("Child")){
                    selectedAdults += Integer.parseInt(selectedRoom.getText().split("([^\\d.]|\\B\\.|\\.\\B)+")[0]);
                    selectedChildren += Integer.parseInt(selectedRoom.getText().split("([^\\d.]|\\B\\.|\\.\\B)+")[1]);
                }
                else {
                    selectedAdults += Integer.parseInt(selectedRoom.getText().split("([^\\d.]|\\B\\.|\\.\\B)+")[0]);
                }
            }
            logger.info("Selected adults " + selectedAdults);
            logger.info("Selected children " + selectedChildren);
        }
        retryUntilVisible(20, confirmButton);
        clickLinkButton(confirmButton);
    }

    private List<WebElement> waitGetElements(By locator){
        List<WebElement> elements = null;
        try {
            elements =  driver.findElements(locator);
        } catch (StaleElementReferenceException e) {
            logger.error("Stale element exception: {} occured." + e.getMessage());
            waitGetElements(locator);
        }
        return elements;
    }

    public void printRecommendedIfExists() {
        ArrayList<String> tabs2 = new ArrayList<> (driver.getWindowHandles());
        driver.switchTo().window(tabs2.get(1));
        retryUntilVisible(30, roomDiv);
        List<WebElement> recommendedComboList = waitUntilAllElementVisible(recommendedComboDiv);
        if (!recommendedComboList.isEmpty()){
            System.out.println("Printing recommended combo.");
            for (WebElement combo : recommendedComboList){
                printRoomdetails(combo.findElement(By.xpath("//div[contains(@class, 'roomRow')]")));
                System.out.println("============");
            }
        } else {
            System.out.println("There are no recommended combo.");
        }

    }

    private void printRoomdetails(WebElement element) {
        List<WebElement> recommendedRoomOption = element.findElements(By.xpath("//span[contains(@class, 'roomTag')]/following-sibling::p"));
        System.out.println("Recommended room name is " + recommendedRoomOption.get(0).getText());
        System.out.println("Recommended room guests are " + recommendedRoomOption.get(1).getText());
    }
}
