package com.company.test.testcases.ui;

import com.company.test.lib.Base.TestBase;
import org.testng.Assert;
import org.testng.annotations.*;

public class SeleniumTest extends TestBase
{
    @BeforeClass
    public void setUp() {
        // Navigate to Google
        getDriver().get("https://www.google.com");
        navigateTo("https://www.google.com");
    }

    @Test
    public void testGoogleTitle() {
        // Verify the title
        Assert.assertEquals(getDriver().getTitle(), "hjkhjk");
    }

    @AfterClass
    public void tearDown() {
    }
}
