package com.testtribe.e2etest.drivers.types;

import com.testtribe.e2etest.drivers.DriverFactory;
import com.testtribe.e2etest.drivers.DriverManager;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.Rule;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testcontainers.containers.BrowserWebDriverContainer;
import org.testcontainers.containers.DefaultRecordingFileFactory;

import java.io.File;

import static org.testcontainers.containers.BrowserWebDriverContainer.VncRecordingMode.RECORD_ALL;

public class FirefoxManager extends DriverManager {
    private static final String REPORTS_PATH = "./target/surefire-reports";
    @Rule
    public BrowserWebDriverContainer firefox =
            new BrowserWebDriverContainer<>()
                    .withCapabilities(new FirefoxOptions())
                    .withRecordingMode(RECORD_ALL, new File(REPORTS_PATH))
                    .withRecordingFileFactory(new DefaultRecordingFileFactory());
    @Override
    public WebDriver getLocalDriver() {
        WebDriverManager.firefoxdriver().setup();
        WebDriver driver = new FirefoxDriver();
        driver.manage().window().maximize();
        return driver;
    }

    @Override
    public RemoteWebDriver getRemoteDriver() {
        firefox.start();
        RemoteWebDriver driver = firefox.getWebDriver();
        return driver;
    }
}
