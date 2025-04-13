package com.company.test.lib.Utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {

    private static Properties properties = new Properties();

    static {
        try {
            FileInputStream fis = new FileInputStream("src/main/resources/config.properties");
            properties.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getBrowserFromConfig() {
        return properties.getProperty("browser", "chrome"); // Default to chrome if not specified
    }

    public static String getUseGridFromConfig() {
        return properties.getProperty("useGrid", "false"); // Default to chrome if not specified
    }

    public static String getHubUrlFromConfig() {
        return properties.getProperty("hubUrl", "http://localhost:4444/wd/hub"); // Default to chrome if not specified
    }

    public static String getPlatformFromConfig() {
        return properties.getProperty("platform", "LINUX"); // Default to chrome if not specified
    }
}
