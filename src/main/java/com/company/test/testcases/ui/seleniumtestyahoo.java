package com.company.test.testcases.ui;

import com.company.test.lib.Base.TestBase;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class seleniumtestyahoo extends TestBase
{
    @BeforeClass
    public void setUp() {
        // Navigate to Yahoo
        getDriver().get("https://www.youtube.com");
        navigateTo("https://www.youtube.com");
    }

    @Test
    public void testYahooTitle() {
        // Verify the title
        Assert.assertTrue(getDriver().getTitle().toLowerCase().contains("youtube"));
    }

    @AfterClass
    public void afterClass() {
    }
}
