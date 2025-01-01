package com.company.test.testcases;

import com.company.test.lib.Base.TestBase;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class SeleniumTest extends TestBase
{
    @BeforeMethod
    public void setUp() {
        // Navigate to Google
        driver.get("https://www.google.com");
        navigateTo("https://www.google.com");
    }

    @Test
    public void testGoogleTitle() {
        // Verify the title
        Assert.assertEquals(driver.getTitle(), "Google");
    }

    @AfterMethod
    public void tearDown() {
        quitDriver();
    }
}
