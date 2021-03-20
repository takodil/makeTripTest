package com.testtribe.e2etest.drivers;

import com.testtribe.e2etest.drivers.types.ChromeManager;
import com.testtribe.e2etest.drivers.types.FirefoxManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.openqa.selenium.WebDriver;

public class LocalDriver {
    private static WebDriver driver;
    private static final Logger logger = LoggerFactory.getLogger(LocalDriver.class);
    public static WebDriver setUp(String browserName) {
        switch (BrowserType.valueOf(browserName.toUpperCase())) {
            case CHROME:
                driver = new ChromeManager().getLocalDriver();
                break;
            case FIREFOX:
                driver = new FirefoxManager().getLocalDriver();
                break;
            default:
                logger.error("Unsupported browser. Use Chrome or firefox for local execution.");
        }
        return driver;
    }
}
