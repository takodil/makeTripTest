package com.testtribe.e2etest.drivers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

public abstract class DriverManager {

    public abstract WebDriver getLocalDriver();
    public abstract RemoteWebDriver getRemoteDriver();
}
