package apiFrameworkBDD.stepDefinations;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;
import static org.hamcrest.Matchers.equalTo;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import resources.TestdataBuild;
import resources.Utils;
import resources.demo;
import serializationtest.AddPlace;
import serializationtest.Location;

public class stepDefination extends Utils {
	RequestSpecification res;
	ResponseSpecification resp;
	Response reponse;
	String place_id;
	JsonPath js;
	static String place;

	TestdataBuild data = new TestdataBuild();
	
	@Given("Add Place Payload with {string} {string} {string}")
	public void add_place_payload_with(String name, String language, String address) throws IOException {
		
		//resp = new ResponseSpecBuilder().expectStatusCode(200)
				//.expectContentType(ContentType.JSON).build();
		res = given().spec(requestSpecification())
				.body(data.addPlacePayLoad(name,language,address));
	}
	
	@When("user calls {string} with {string} http request")
	public void user_calls_with_post_http_request(String resource, String method) {
		
		demo resourceAPI = demo.valueOf(resource);
		System.out.println(resourceAPI.getResource());
		
		resp = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
		
		if(method.equalsIgnoreCase("POST"))
		{
			reponse = res.when().post(resourceAPI.getResource());
		}
		else if(method.equalsIgnoreCase("GET"))
		{
			reponse = res.when().get(resourceAPI.getResource());
		}
	}
	@Then("the API call is success with status code {int}")
	public void the_api_call_is_success_with_status_code(Integer int1) {
		assertEquals(reponse.getStatusCode(),200);
	}
	@Then("{string} in response body is {string}")
	public void in_response_body_is(String string, String string2) {
	 
	   assertEquals(getJsonPath(reponse,string),string2);	   
	}
	@Then("Verify place_id created maps to {string} using {string}")
	public void verify_place_id_created_maps_to_using(String expectedName, String string2) throws IOException 
	{
		place = getJsonPath(reponse,"place_id");
		res = given().spec(requestSpecification()).queryParam("place_id", place);
		user_calls_with_post_http_request(string2,"GET");
		String actualName = getJsonPath(reponse, "name");
		assertEquals(actualName,expectedName);
	}
	
	@Given("DeletePlace Payload")
	public void deleteplace_Payload() throws IOException
	{
		res = given().spec(requestSpecification())
				.body(data.deletePlacePayload(place));	
	}
}