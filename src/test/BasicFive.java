package test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.testng.Assert;

import file.ReUsableMethodsSix;
import file.payloadFour;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class BasicFive {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//validate if  Add Place API is working as expected
				//Add place -> Update Place with New Address -> Get Place to validate if New address is present in response
				
				//given - all input details
				//when - submit the API
				//then - validate the response
				
				
				
				//Add place
				RestAssured.baseURI = "https://rahulshettyacademy.com";
				String response = given().log().all().queryParam("key","qaclick123").header("Content-Type","application/json")
				.body(payloadFour.AddPlace()).when().post("maps/api/place/add/json")
				.then().assertThat().statusCode(200).body("scope", equalTo("APP")).header("server", "Apache/2.4.52 (Ubuntu)").extract().response().asString();
				// we can validate headers body
				//when header used before then it is input but used after then it is used for output validation
			
			
				System.out.println(response);
				
				JsonPath js = new JsonPath(response);
				String placeId = js.getString("place_id");
				
				System.out.println(placeId);
				
			
				//update place
				String newAddress = "winter walk, Africa";
				
				given().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json")
				.body("{\r\n"
						+ "\"place_id\":\""+placeId+"\",\r\n"
						+ "\"address\":\""+newAddress+"\",\r\n"
						+ "\"key\":\"qaclick123\"\r\n"
						+ "}")
				.when().put("maps/api/place/update/json")
				.then().log().all().assertThat().statusCode(200).body("msg", equalTo("Address successfully updated"));
				
				
				
				//Get place
				String getResponse = given().log().all().queryParam("key", "qaclick123").queryParam("place_id", placeId)
				.when().get("maps/api/place/get/json")
				.then().log().all().assertThat().statusCode(200).extract().response().asString();
				
				JsonPath js1 = ReUsableMethodsSix.rawToJson(getResponse);
				String actualAddress = js1.getString("address");
				
				System.out.println(actualAddress);
				
				Assert.assertEquals(actualAddress,newAddress);
		}
}