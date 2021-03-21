package com.testtribe.e2etest.pages;

import com.testtribe.e2etest.drivers.DriverFactory;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class BasePage {
    protected WebDriver driver;
    private WebDriverWait webDriverWait;
    protected Logger logger = LogManager.getLogger(BasePage.class);
    private String browserName;

    public BasePage() {
        this.driver = DriverFactory.getCurrentDriver();
        this.webDriverWait = new WebDriverWait(driver, 60, 1000);
        this.browserName = ((RemoteWebDriver) driver).getCapabilities().getBrowserName();
    }


    protected void openPage(String url) {
        driver.manage().window().maximize();
        driver.get(url);
        waitForPageLoad();
    }

    protected void clickLinkButton(By locator) {
        retryUntilClickable(10, locator);
        try {
            WebElement element = driver.findElement(locator);
            element.click();
        } catch (StaleElementReferenceException e) {
            logger.error("Error stale element " + locator);
            clickLinkButton(locator);
        }
        logger.info("Clicking now element  " + locator);
    }

    protected void clickLinkButtonByElement(WebElement element) {
        webDriverWait.until(ExpectedConditions.elementToBeClickable(element));
        element.click();
        logger.info("Clicking now element  " + element);
    }

    protected void clickLinkButtonByElementWithJS(WebElement element) {
        webDriverWait.ignoring(StaleElementReferenceException.class).until(ExpectedConditions.elementToBeClickable(element));
        try {
            logger.info("Clicking now element  with JS" + element);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
        } catch (StaleElementReferenceException e) {
            logger.error("Error stale element " + element);
            clickLinkButtonByElementWithJS(element);
        }
    }

    protected void sendKeysTextField(By locator, String keys) {
        retryUntilClickable(10, locator);
        WebElement element = driver.findElement(locator);
        for (int i = 0; i < keys.length(); i++) {
            element.sendKeys(Character.toString(keys.charAt(i)));
        }
    }

    protected WebElement waitUntilElementClickable(By locator) {
        logger.info("wait element" + locator);
        return webDriverWait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    protected WebElement retryUntilUpdatedVisible(int maxTries, By locator) {
        WebElement element = null;
        for (int counter = 1; counter < maxTries; counter++) {
            logger.info("Retrying until updated visible " + counter + " for locator " + locator);
            try {

                element = webDriverWait.ignoring(StaleElementReferenceException.class).until(ExpectedConditions.visibilityOfElementLocated(locator));
                if (element.isDisplayed()) {
                    break;
                }
            } catch (StaleElementReferenceException e) {
                logger.info("StaleElementReferenceException " + locator);
            }
        }
        return element;
    }

    protected WebElement waitUntilElementVisible(By locator) {
        logger.info("wait element" + locator);
        WebElement element = null;
        try {
            element = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        } catch (StaleElementReferenceException e) {
            logger.error("Stale element" + locator);
            waitUntilElementVisible(locator);
        }
        return element;
    }

    protected List<WebElement> waitUntilAllElementVisible(By locator) {
        logger.info("wait element and return" + locator);
        driver.findElements(locator);
        List<WebElement> elements = new ArrayList<>();
        try {
            if (driver.findElements(locator).size() > 0) {
                elements = driver.findElements(locator);
            }
        } catch (StaleElementReferenceException e) {
            logger.error("Stale element. " + locator);
            waitUntilAllElementVisible(locator);
        }
        return elements;
    }

    protected void retryUntilClickable(int maxTries, By locator) {
        for (int counter = 1; counter < maxTries; counter++) {
            logger.info("Retrying until clickable " + counter + " for locator " + locator);
            try {
                WebElement element = waitUntilElementVisible(locator);
                if (element.isDisplayed()) {
                    break;
                }
            } catch (TimeoutException e) {
                logger.info("Element not visible yet " + locator);
            }
        }
    }

    protected void retryUntilVisible(int maxTries, By locator) {
        for (int counter = 1; counter < maxTries; counter++) {
            logger.info("Retrying until visible " + counter + " for locator " + locator);
            waitUntilElementVisible(locator);
        }
    }

    protected void waitForPageLoad() {
        ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
    }

    protected void scrollToElement(By locator) {
        WebElement element = driver.findElement(locator);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    protected void scrollToElementByElement(WebElement element) {
        Actions actions = new Actions(driver);
        actions.moveToElement(element);
        actions.perform();
    }

    protected void scrollUntilElement(By locator) {
        logger.info("Scrolling through the page until " + locator);
        float scroll = (driver.manage().window().getSize().getHeight()) / 10;
        float windowHeight = (driver.manage().window().getSize().getHeight());
        JavascriptExecutor js = (JavascriptExecutor) driver;
        while (scroll <= windowHeight) {
            js.executeScript("window.scrollBy(0," + scroll + ")");
            if (driver.findElements(locator).size() > 0) {
                logger.info("Scrolling is done, element found.");
                break;
            }
            scroll++;
        }
    }


}
