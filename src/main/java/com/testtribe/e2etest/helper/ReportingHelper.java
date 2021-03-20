package com.testtribe.e2etest.helper;

import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.IOException;

public class ReportingHelper {
    @Attachment(value = "{0}", type = "image/png")
    public static byte[] takeScreenShotToReport(String attachName, WebDriver driver) throws IOException {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }

    @Attachment(value = "{0}", type = "text/plain")
    public String reportText(String reportLine, String message) {
        return message;
    }
}
