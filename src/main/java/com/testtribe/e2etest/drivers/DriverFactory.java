package com.testtribe.e2etest.drivers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testcontainers.containers.BrowserWebDriverContainer;

import java.util.function.BiFunction;
import java.util.function.Function;

public class DriverFactory {
    private static ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<WebDriver>();

    public static WebDriver getDriver(String config, String  environment) {

        WebDriver driver = null;

        String browser_env = System.getProperty("browser_env", "local");

        if (browser_env.equals("remote")) {
            String browserName = System.getProperty("browser", "chrome");
            Function<String, RemoteWebDriver> localDriverSetUp = RemoteDriver::setUp;
            driver = localDriverSetUp.apply(browserName);
        } else if (browser_env.equals("local")) {
            String browserName = System.getProperty("browser", "chrome");
            Function<String, WebDriver> localDriverSetUp = LocalDriver::setUp;
            driver = localDriverSetUp.apply(browserName);
        }
        driverThreadLocal.set(driver);
        return driver;
    }

    public static WebDriver getCurrentDriver() {
        return driverThreadLocal.get();
    }

    public static void removeDriver() {
        driverThreadLocal.remove();
    }
}
