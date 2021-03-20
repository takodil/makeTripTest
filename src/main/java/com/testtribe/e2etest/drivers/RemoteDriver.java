package com.testtribe.e2etest.drivers;

import com.testtribe.e2etest.drivers.types.ChromeManager;
import com.testtribe.e2etest.drivers.types.FirefoxManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.BrowserWebDriverContainer;

public class RemoteDriver {
    public static RemoteWebDriver driver;
    private static final Logger logger = LoggerFactory.getLogger(RemoteDriver.class);

public static RemoteWebDriver setUp(String browserName) {
    switch (BrowserType.valueOf(browserName.toUpperCase())) {
        case CHROME:
            driver = new ChromeManager().getRemoteDriver();
            break;
        case FIREFOX:
            driver = new FirefoxManager().getRemoteDriver();
            break;
        default:
            logger.error("Unsupported browser. Use Chrome or firefox for local execution.");
    }
    return driver;
}

}
