package com.company.test.lib.DriverManagers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import io.github.bonigarcia.wdm.WebDriverManager;

public class FirefoxWebDriverManager implements WebDriverManagerRule {

    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    @Override
    public WebDriver getDriver() {
        if (driver.get() == null) {
            WebDriverManager.firefoxdriver().setup();
            driver.set(new FirefoxDriver());
        }
        return driver.get();
    }
}
