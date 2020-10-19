package com.automation.utility;

import java.util.List;

import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;

public class ResponseUtils {
    public static Response response;

    public static String getDataFromResponseUsingJsonPath(String jsonPath) {
        return response.then().extract().jsonPath().getString(jsonPath);
    }

    public static List<String>
           getListFromResponseUsingJsonPath(String jsonPath) {
        System.out.println(
                response.then().extract().jsonPath().getList(jsonPath));
        return response.then().extract().jsonPath().getList(jsonPath);
    }

    public static void assertReponseStatus(int expectedStatusCode) {
        response.prettyPrint();
        response.then().statusCode(expectedStatusCode);

    }

   

}
