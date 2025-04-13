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
    public static WebDriver driver;

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
        return webDriverManager.getDriver();
    }


    // Navigate to URL
    public void navigateTo(String url) {
        driver.get(url);
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
        // Ensure a fresh driver instance
        System.out.println("useGrid option is set to : "+ useGrid);
        if(useGrid){
            huburl = ConfigReader.getHubUrlFromConfig();
            ChromeOptions chromeOptions = new ChromeOptions();
            FirefoxOptions firefoxOptions = new FirefoxOptions();

            System.out.println("Initializing RemoteWebDriver for Selenium Grid: ");
            if(browser.equalsIgnoreCase("chrome")){
                chromeOptions.setPlatformName(ConfigReader.getPlatformFromConfig()); // Ensure this matches the platform on your nodes
                driverThreadLocal.set(new RemoteWebDriver(new URL(huburl), chromeOptions));
            }else if(browser.equalsIgnoreCase("firefox")){
                firefoxOptions.setPlatformName(ConfigReader.getPlatformFromConfig()); // Ensure this matches the platform on your nodes
                driverThreadLocal.set(new RemoteWebDriver(new URL(huburl), firefoxOptions));
            }
        } else if (driverThreadLocal.get() == null) {
            driverThreadLocal.set(getDriver()); // Initialize if it's not already initialized
        }
        driver = driverThreadLocal.get();
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
            driver = null;
        }
    }
}
