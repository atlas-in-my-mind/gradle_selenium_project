package com.company.test.lib.DriverManagers;

public class WebDriverManagerFactory {

    public static WebDriverManagerRule getWebDriverManager(String browser) {
        if (browser.equalsIgnoreCase("chrome")) {
            return new ChromeWebDriverManager();
        } else if (browser.equalsIgnoreCase("firefox")) {
            return new FirefoxWebDriverManager();
        }
        throw new IllegalArgumentException("Unsupported browser: " + browser);
    }
}
