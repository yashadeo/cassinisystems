package com.cassini.test.framework.helpers.screenshot_helper;

import com.cassini.test.framework.helpers.WebDriverHelper;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriverException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import static com.cassini.test.framework.helpers.WebDriverHelper.getWebDriver;

public class ScreenshotHook {

    private static final Logger LOG = LoggerFactory.getLogger(ScreenshotHook.class);


   @After
    public void embedScreenshot(Scenario scenario) {
        try {
            if (scenario.isFailed()) {
                scenario.write(WebDriverHelper.getWebDriver().getCurrentUrl());
                byte[] screenShot = ((TakesScreenshot) getWebDriver()).getScreenshotAs(OutputType.BYTES);
                scenario.embed(screenShot, "image/png");
            }

        } catch (WebDriverException | ClassCastException wde) {
            LOG.error(wde.getMessage());
        } finally {
            getWebDriver().switchTo().defaultContent();
        }
    }
}
