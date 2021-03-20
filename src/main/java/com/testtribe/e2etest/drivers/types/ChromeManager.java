package com.testtribe.e2etest.drivers.types;

import com.testtribe.e2etest.drivers.DriverManager;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.Rule;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testcontainers.containers.BrowserWebDriverContainer;
import org.testcontainers.containers.DefaultRecordingFileFactory;
import static org.testcontainers.containers.BrowserWebDriverContainer.VncRecordingMode.RECORD_ALL;

import java.io.File;

public class ChromeManager extends DriverManager {
    private static final String REPORTS_PATH = "./target";
    @Rule
    public BrowserWebDriverContainer chrome =
            new BrowserWebDriverContainer<>()
                    .withCapabilities(new ChromeOptions())
                    .withRecordingMode(RECORD_ALL, new File(REPORTS_PATH))
                    .withRecordingFileFactory(new DefaultRecordingFileFactory());
    @Override
    public WebDriver getLocalDriver() {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        return driver;
    }

    @Override
    public RemoteWebDriver getRemoteDriver() {
        chrome.start();
        RemoteWebDriver driver = chrome.getWebDriver();
        return driver;
    }
}
