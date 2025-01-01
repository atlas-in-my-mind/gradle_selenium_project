package com.company.test.lib.Base;

import com.company.test.lib.DriverManagers.WebDriverManagerFactory;
import com.company.test.lib.DriverManagers.WebDriverManagerRule;
import com.company.test.lib.Utils.ConfigReader;
import org.openqa.selenium.WebDriver;


public class TestBase {

    protected WebDriverManagerRule webDriverManager;
    public WebDriver driver;

    public TestBase() {
        // Load the browser type from config file
        String browser = ConfigReader.getBrowserFromConfig();
        this.webDriverManager = WebDriverManagerFactory.getWebDriverManager(browser);
        driver = getDriver();
    }

    // Get WebDriver instance
    public WebDriver getDriver() {
        return webDriverManager.getDriver();
    }

    // Quit WebDriver
    public void quitDriver() {
        if (getDriver() != null) {
            getDriver().quit();
        }
    }

    // Navigate to URL
    public void navigateTo(String url) {
        getDriver().get(url);
    }
}
