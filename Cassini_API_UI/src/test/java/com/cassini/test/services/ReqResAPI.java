package com.cassini.test.services;

import com.cassini.test.enums.EndPOints;
import com.cassini.test.framework.helpers.APIMethods;
import com.jayway.restassured.response.Response;
import org.hamcrest.Matchers;
import java.io.IOException;

import static org.hamcrest.Matchers.equalTo;


public class ReqResAPI extends APIMethods {
    static String url = EndPOints.USER_REGISTRATION.getUrl();
    static Response response;

    public static Response userRegistration(String fileName) throws IOException {
        url = EndPOints.USER_REGISTRATION.getUrl();
        //creating a object from the lombok class and setting the values
        /* APPROACH 1
        User userRegistration = new User();
        userRegistration.setEmail(email);
        userRegistration.setPassword(password);
        // COnverting an objectto the JSON string
        String registrationPayload = gson().toJson(userRegistration);
  */

        //Sending a post request APPROACH 2 :- COnverting JSON to JSONString (Serializetion)
        return givenConfig().body(convertJSONFiletoJSONString(fileName)).log().all().when().post(url);
    }

    public static Response userLogin(String fileName) throws IOException {
        url = EndPOints.USER_LOGIN.getUrl();
        return givenConfig().body(convertJSONFiletoJSONString(fileName)).log().all().when().post(url);
    }

    public static void getSpecificResource(String id){
        url = EndPOints.RESOURCE.getUrl();
        response = givenConfig().queryParam("id",id).log().all().when().get(url);
    }


    public static void validateSpecificResource(int id,String name, int year, String color, String pantone_value){
        response.then().assertThat().body("data.id", equalTo (id))
                .body("data.name",equalTo(name))
                .body("data.year",equalTo(year))
                .body("data.color",equalTo(color))
                .body("data.pantone_value",equalTo(pantone_value));

    }

}


