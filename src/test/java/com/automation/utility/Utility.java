package com.automation.utility;

import static io.restassured.RestAssured.given;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.everit.json.schema.Schema;
import org.everit.json.schema.loader.SchemaLoader;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import com.automation.base.SerenityBase;
import com.automation.endpoints.APIEndPoints;
import com.google.common.base.Charsets;
import com.google.common.collect.Multimap;
import com.google.common.io.Resources;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

import cucumber.api.DataTable;
import io.restassured.RestAssured;
import io.restassured.config.EncoderConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.config.SSLConfig;
import io.restassured.http.ContentType;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;

public class Utility extends SerenityBase {
	private static Map<String, WebDriver> drivers = new HashMap<String, WebDriver>();
	
    public static EncoderConfig encoderconfig = new EncoderConfig();

       public static synchronized String alterJson(String jsonName,
            Map<String, String> jsonValues) {
        String jsonString = null;
        try {
            URL file = Resources.getResource(
                    "configFiles/jsonFiles/RequestJson/" + jsonName + ".txt");
            jsonString = Resources.toString(file, Charsets.UTF_8);

            for (Map.Entry<String, String> keyVal : jsonValues.entrySet()) {
                jsonString = jsonString.replaceAll(keyVal.getKey(),
                        keyVal.getValue());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonString;
    }


    public static Response getCallWithHeaderAndQueryParam(
            Map<String, String> headers, Map<String, String> queryparam, String URL) {
        return SerenityRest.given().relaxedHTTPSValidation().headers(headers)
                .queryParams(queryparam).log().all().when()
                .get(URL);
    }


    public static Response putCallWithHeaderAndBodyParam(
            Map<String, String> headers, String jsonBodyAsString, String URL) {
        return SerenityRest.given().relaxedHTTPSValidation().headers(headers)
                .body(jsonBodyAsString).log().all().when().put(URL);
    }

    
    

    public static String createEndPoint(String base_url, String ApiEndpoint) {
        System.out.println(base_url + ApiEndpoint);
        return base_url + ApiEndpoint;
    }

    public Map<String, String> getMapOfJsonPath(Class cls) {
        Map<String, String> jsonPathMap = new HashMap<String, String>();
        try {

            for (Field fields : cls.getFields()) {
                jsonPathMap.put(fields.getName(),
                        fields.get(fields.getName()).toString());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return jsonPathMap;
    }

    public static void putVariablesInMap(Class clz) throws Exception {
        Field[] fields = clz.getFields();
        for (Field field : fields) {
            PropertyHolder.setProperty(field.getName(),
                    (String) field.get(field));
        }
    }

    public static Response postCallWithHeaderAndBodyParam(
            Map<String, String> headers, String jsonBodyAsString, String URL) {
        return SerenityRest.given()
                .config(RestAssured.config().encoderConfig(encoderconfig
                        .appendDefaultContentCharsetToContentTypeIfUndefined(
                                false)))
                .relaxedHTTPSValidation().headers(headers)
                .body(jsonBodyAsString).log().all().when().post(URL);
    }
    public static Map<String, String> convertDataTableIntoMap(DataTable table) {
        Map<String, String> jsonValues = new HashMap<String, String>();
        for (int i = 1; i < table.raw().size(); i++) {
            jsonValues.put(table.raw().get(i).get(1),
                    table.raw().get(i).get(2));
        }
        return jsonValues;

    }
    public static String generateRandomId() {

		Random r = new Random();
		int numbers = 1000000000 + (int) (r.nextDouble() * 999999999);
		String random_number = Integer.toString(numbers);
		System.out.println(random_number);

		return random_number;
	}

}
