package com.cassini.test.framework.helpers;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.specification.RequestSpecification;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

import static com.jayway.restassured.RestAssured.given;

/**
 * Every Api Step definition class should extend this class
 */

public class APIMethods {
    private static Gson gson;
    private static String RESOURCE_LOCATION = "src" + File.separator + "test" + File.separator + "resources" + File.separator + "Data" + File.separator;
    static {
        RestAssured.baseURI = UrlBuilder.getBasePathURI().toString();

    }

    public static RequestSpecification givenConfig() {
        RestAssured.useRelaxedHTTPSValidation();
        return given().
                header("Accept-Language", "en").header("Content-Type", "application/json");
    }

    public static String convertJSONFiletoJSONString(String fileName) throws IOException {
        String stringJson = FileUtils.readFileToString(new File(RESOURCE_LOCATION,fileName));
        return stringJson;
    }

    //Specify all one time default Gson config
       public static Gson gson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        // if uncommented will also create Json for fields which are null
        //   gsonBuilder.serializeNulls();
        gson = gson(gsonBuilder);
        return gson;
    }

    //Custom Gson config to override Default Gson  configuration
    public static Gson gson(GsonBuilder gsonBuilder) {
        gson = gsonBuilder.create();
        return gson;
    }


}