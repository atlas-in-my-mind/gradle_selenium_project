package com.company.test.lib.DriverManagers;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import io.github.bonigarcia.wdm.WebDriverManager;

public class ChromeWebDriverManager implements WebDriverManagerRule {

    private final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    @Override
    public WebDriver getDriver() {
        if (driver.get() == null) {
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--remote-allow-origins=*");
            options.addArguments("--start-maximized"); // Start Chrome maximized
            options.addArguments("--disable-notifications"); // Disable browser notifications
            options.addArguments("--incognito"); // Start Chrome in Incognito mode

            driver.set(new ChromeDriver(options));
        }
        return driver.get();
    }
}
