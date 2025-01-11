package com.company.test.lib.Base;

import com.company.test.lib.DriverManagers.WebDriverManagerFactory;
import com.company.test.lib.DriverManagers.WebDriverManagerRule;
import com.company.test.lib.Utils.ConfigReader;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;

public class TestBase {

    protected WebDriverManagerRule webDriverManager;
    public WebDriver driver;

    public TestBase() {
        // Load the browser type from config file
        String browser = ConfigReader.getBrowserFromConfig();
        this.webDriverManager = WebDriverManagerFactory.getWebDriverManager(browser);
    }

    // Get WebDriver instance
    public WebDriver getDriver() {
        return webDriverManager.getDriver();
    }

    // Quit WebDriver
    public void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null; // Clear driver reference after quitting
        }
    }

    // Close WebDriver
    public void closeDriver() {
        if (driver != null) {
            driver.close();
        }
    }

    // Navigate to URL
    public void navigateTo(String url) {
        driver.get(url);
    }

    @BeforeSuite
    public void beforeSuite() {
        System.out.println("Executing BeforeSuite - suite setup.");
        // Reinitialize WebDriver for each suite execution to avoid stale session
    }

    @AfterSuite
    public void afterSuite() {
        System.out.println("Executing AfterSuite - Suite Finished");
        // Quit WebDriver after suite execution
        quitDriver();
    }

    @BeforeClass
    public void beforeClass() {
        System.out.println("Executing BeforeClass - class setup.");
        // Ensure a fresh driver instance
        if (driver == null) {
            driver = getDriver(); // Initialize if it's not already initialized
        }
    }

    @AfterClass
    public void afterClass() {
        System.out.println("Executing AfterClass - Closing Driver");
        // Quit driver after each class
        quitDriver();
    }

    @BeforeTest
    public void beforeTest() {
        System.out.println("Executing BeforeTest - test setup.");
        // Test-level setup
    }

    @AfterTest
    public void afterTest() {
        System.out.println("Executing AfterTest - test teardown.");
        // Test-level teardown
    }
}
