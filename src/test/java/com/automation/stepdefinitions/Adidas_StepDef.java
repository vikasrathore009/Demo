package com.automation.stepdefinitions;

import static com.automation.utility.ResponseUtils.response;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.digester.SetPropertyRule;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;

import com.automation.base.SerenityBase;
import com.automation.endpoints.APIEndPoints;
import com.automation.utility.PropertyHolder;
import com.automation.utility.PropertyManager;
import com.automation.utility.Utility;

import cucumber.api.DataTable;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.path.json.JsonPath;
import net.serenitybdd.rest.SerenityRest;

public class Adidas_StepDef extends SerenityBase {
	String endPoint;
	public String CONTENTTYPE = "Content-Type";
	String idToBeUpdated;
	PropertyManager p = new PropertyManager();
	Map<String, String> headers = new HashMap<String, String>(10);
	Map<String, String> queryParam = new HashMap<String, String>(10);
	Map<String, String> jsonValues = new HashMap<String, String>(10);;


	@Given("^User set valid endpoint to \"([^\"]*)\"$")
	public void user_set_valid_endpoint_to_something(String strArg1) throws Throwable {
		endPoint = Utility.createEndPoint(p.valueFromConfig("Base_URL"), PropertyHolder.getProperty(strArg1));
		PropertyHolder.setProperty("endPoint", endPoint);
	}

	@When("^User send the \"([^\"]*)\" request with valid json having following details$")
	public void user_send_the_something_request_with_valid_json_having_following_details(String method, DataTable table)
			throws Throwable {
		if (method.equalsIgnoreCase("post")) {
			jsonValues = Utility.convertDataTableIntoMap(table);
			idToBeUpdated = Utility.generateRandomId();
			jsonValues.put("idToBeUpdated", idToBeUpdated);
			String jsonString = Utility.alterJson("CreateUpdatePet", jsonValues);
			headers.put(CONTENTTYPE, "application/json");
			response = Utility.postCallWithHeaderAndBodyParam(headers, jsonString, endPoint);
			response.prettyPrint();
		} else {
			jsonValues = Utility.convertDataTableIntoMap(table);
			idToBeUpdated = Utility.generateRandomId();
			jsonValues.put("idToBeUpdated", idToBeUpdated);
			String jsonString = Utility.alterJson("CreateUpdatePet", jsonValues);
			headers.put(CONTENTTYPE, "application/json");
			response = Utility.putCallWithHeaderAndBodyParam(headers, jsonString, endPoint);
			response.prettyPrint();
		}

	}

	@When("^User send the Get request with query param$")
	public void user_send_the_Get_request_with_query_param(DataTable table) throws Exception {

		queryParam = Utility.convertDataTableIntoMap(table);
		headers.put("Content-Type", "application/json");
		response = Utility.getCallWithHeaderAndQueryParam(headers, queryParam, endPoint);
		response.prettyPrint();

	}

	@Then("^Server should return \"([^\"]*)\" Status$")
	public void server_should_return_Status(int arg1) {
		Assert.assertEquals(arg1, response.getStatusCode());
	}

	@Then("^pet id is generated in response$")
	public void pet_id_is_generated_in_response() {
		JsonPath js = new JsonPath(response.asString());
		String petID = js.get("id").toString();
		Assert.assertEquals(petID, idToBeUpdated);
		APP_LOG.info("Newly Generated Pet Id= " + petID);
	}

	@And("^verify response has (.+) pets$")
	public void verify_response_has_pets(String status) throws Throwable {
		if (response.asString().startsWith("[")) {
			JSONArray jsonArray = new JSONArray(response.asString());
			for (int i = 0; i < jsonArray.length(); i++) {
				Assert.assertEquals(status, response.then().extract().jsonPath().get("[" + i + "].status"));
				String reponseStatus = response.then().extract().jsonPath().get("[" + i + "].status");
				APP_LOG.info("Response Status= " + reponseStatus);

			}
		} else
			Assert.assertEquals(status, response.then().extract().jsonPath().get("status"));
	}
}
