package com.company.test.testcases;

import com.company.test.lib.Base.TestBase;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class seleniumtestyahoo extends TestBase
{
    @BeforeClass
    public void setUp() {
        // Navigate to Yahoo
        driver.get("https://www.yahoo.com");
        navigateTo("https://www.yahoo.com");
    }

    @Test
    public void testYahooTitle() {
        // Verify the title
        Assert.assertTrue(driver.getTitle().toLowerCase().contains("yahoo"));
    }

    @AfterClass
    public void afterClass() {
    }
}
