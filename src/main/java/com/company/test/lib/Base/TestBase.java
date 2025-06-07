package com.company.test.lib.Base;

import com.company.test.lib.DriverManagers.WebDriverManagerFactory;
import com.company.test.lib.DriverManagers.WebDriverManagerRule;
import com.company.test.lib.Utils.ConfigReader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.*;

import java.net.MalformedURLException;
import java.net.URL;

public class TestBase {

    protected WebDriverManagerRule webDriverManager;
    public static ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    public String browser;
    public boolean useGrid;

    public String huburl;

    public TestBase() {
        // Load the browser type from config file
        browser = ConfigReader.getBrowserFromConfig();
        useGrid = Boolean.parseBoolean(ConfigReader.getUseGridFromConfig());
        this.webDriverManager = WebDriverManagerFactory.getWebDriverManager(browser);
    }

    // Get WebDriver instance
    public WebDriver getDriver() {
        return driverThreadLocal.get();
    }

    // Navigate to URL
    public void navigateTo(String url) {
        getDriver().get(url);
    }

    @AfterSuite
    public void afterSuite() {
        System.out.println("Executing AfterSuite - Suite Finished");
        // Quit WebDriver after suite execution
        quitDriver();
    }

    @BeforeSuite
    public void beforeSuite() throws MalformedURLException {
        System.out.println("Executing beforeSuite - class setup.");
    }

    @AfterClass
    public void afterClass() {
        System.out.println("Executing AfterClass - Closing Driver");
        // Quit driver after each class
        quitDriver();
    }

    @BeforeClass
    public void beforeClass() throws MalformedURLException {
        System.out.println("Executing beforeClass...");
        System.out.println("useGrid option is set to : "+ useGrid);

        if (driverThreadLocal.get() == null) {
            if(useGrid){
                huburl = ConfigReader.getHubUrlFromConfig();
                if (browser.equalsIgnoreCase("chrome")) {
                    ChromeOptions options = new ChromeOptions();
                    options.setPlatformName(ConfigReader.getPlatformFromConfig());
                    driverThreadLocal.set(new RemoteWebDriver(new URL(huburl), options));
                } else if (browser.equalsIgnoreCase("firefox")) {
                    FirefoxOptions options = new FirefoxOptions();
                    options.setPlatformName(ConfigReader.getPlatformFromConfig());
                    driverThreadLocal.set(new RemoteWebDriver(new URL(huburl), options));
                }
            } else {
                // Local browser initialization via your WebDriverManagerRule
                driverThreadLocal.set(webDriverManager.getDriver());
            }
        }
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

    // Quit WebDriver
    public void quitDriver() {
        if (driverThreadLocal.get() != null) {
            driverThreadLocal.get().quit();
            driverThreadLocal.remove(); // Clear thread-local driver instance
        }
    }
}
