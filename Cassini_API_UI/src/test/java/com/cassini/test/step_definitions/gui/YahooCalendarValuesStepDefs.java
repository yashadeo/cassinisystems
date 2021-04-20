package com.cassini.test.step_definitions.gui;

import com.cassini.test.framework.helpers.UrlBuilder;
import com.cassini.test.page_objects.FinancePage;
import com.cassini.test.page_objects.LandingPage;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.testng.Assert;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class YahooCalendarValuesStepDefs {
    private LandingPage landingPage;
    private FinancePage financePage;
    List<String>calendarVals = new ArrayList<>();

    public YahooCalendarValuesStepDefs(LandingPage landingPage, FinancePage financePage) throws IOException {
        this.landingPage = landingPage;
        this.financePage = financePage;
    }

    @Given("^I navigate to the Project \"([^\"]*)\" page$")
    public void iNavigateToTheProjectPage(String page) throws Throwable {
        if (page.equalsIgnoreCase("Home"))
            UrlBuilder.startAtHomePage();
    }

    @When("^user navigates to the calendar page$")
    public void user_navigates_to_the_calendar_page() throws Throwable {

        landingPage.clickFinance();
        //financePage.navigateToCalendarPage();
        landingPage.navigateToCalendarPage();
    }

    @When("capture the calendar values")
    public void capture_the_calendar_values() throws Throwable {
        calendarVals = financePage.captureValues();
    }

    @Then("^the user should see the calendar values as per the below data table$")
    public void the_user_should_see_the_calendar_values_as_per_the_below_data_table(List<String> list) throws Throwable {
        Assert.assertEquals(calendarVals,list);
    }


    @When("^user enters username and clicks the Next button$")
    public void user_enters_username_and_clicks_the_Next_button() throws Throwable {
        landingPage.enterIncorrectLogin();
    }

    @Then("^user should see the error message \"([^\"]*)\" on the screen$")
    public void user_should_see_the_error_message_on_the_screen(String errorMsg) throws Throwable {
        Assert.assertEquals(landingPage.signInErrorMessage(),errorMsg);
    }


}
