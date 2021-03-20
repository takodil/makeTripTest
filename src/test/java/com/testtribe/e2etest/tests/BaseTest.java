package com.testtribe.e2etest.tests;

import com.testtribe.e2etest.drivers.DriverFactory;
import com.testtribe.e2etest.readers.PropertyReader;
import org.apache.log4j.BasicConfigurator;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

import java.io.IOException;

public class BaseTest {
    @BeforeSuite
    public void loadData() {
        PropertyReader pr = new PropertyReader();
        BasicConfigurator.configure();
    }

    @BeforeMethod
    @Parameters(value = {"config", "environment"})
    public void launchBrowser(String config, String environment) {
        DriverFactory.getDriver(config, environment);
    }

    @AfterMethod
    public void closeBrowser() {
        DriverFactory.getCurrentDriver().quit();
        DriverFactory.removeDriver();
    }
}

