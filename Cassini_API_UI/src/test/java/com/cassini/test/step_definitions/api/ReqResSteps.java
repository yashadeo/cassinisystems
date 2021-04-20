package com.cassini.test.step_definitions.api;

import com.cassini.test.services.ReqResAPI;
import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.testng.Assert;

import java.io.IOException;

import static com.cassini.test.services.ReqResAPI.getSpecificResource;

public class ReqResSteps {

    private Response response;

    @When("^user enters valid userID and Password$")
    public void user_enters_valid_userID_and_Password() throws Throwable {
        response = ReqResAPI.userRegistration("UserRegistration.json");
    }

    @Then("^the user should be registered successfully$")
    public void the_user_should_be_registered_successfully() throws Throwable {
        int statusCode = response.getStatusCode();
        JsonPath jsonPath = response.jsonPath();

        String id = jsonPath.get("id");
        String date = jsonPath.get("createdAt");

        Assert.assertTrue(id.length()>0);
        Assert.assertTrue(date.length()>0);
        Assert.assertEquals(statusCode,201);
     //   Assert.assertTrue(str.contains());

    }

    @Then("^the user should be logged-in successfully$")
    public void the_user_should_be_logged_in_successfully() throws Throwable {
        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode,200);
    }

    @When("^user enters valid userID and Password to login$")
    public void userEntersValidUserIDAndPasswordToLogin() throws IOException {
        response = ReqResAPI.userLogin("UserRegistration.json");
    }

    @When("^user sends the request to fetch the resources for the id \"([^\"]*)\"$")
    public void user_sends_the_request_to_fetch_the_resources_for_the_id(String id) throws Throwable {
        getSpecificResource(id);
    }

    @Then("^the user should see the id \"([^\"]*)\",name \"([^\"]*)\", year\"([^\"]*)\",color \"([^\"]*)\" and pantone_value \"([^\"]*)\" in response$")
    public void the_user_should_see_the_id_name_year_color_and_pantone_value_in_response(String id, String name, String year, String color, String pantone_value) throws Throwable {
        int ids = Integer.parseInt(id);
        int years = Integer.parseInt(year);
        ReqResAPI.validateSpecificResource(ids,name,years,color,pantone_value);
    }


    @Then("^the user should see the error code \"([^\"]*)\" in the response$")
    public void the_user_should_see_the_error_code_in_the_response(String errorCode) throws Throwable {
        int code = response.statusCode();
        int error = Integer.parseInt(errorCode);
        Assert.assertEquals(code,error);
    }


}
