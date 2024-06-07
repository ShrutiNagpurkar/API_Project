package serializationtest;

import io.restassured.RestAssured;
import serializationtest.AddPlace;
import serializationtest.Location;

import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.List;

public class TestOne {
	
	//Serilization - java to json
	public static void main(String[] args)
	{
		AddPlace p = new AddPlace();
		p.setAccuracy(50);
		p.setAddress("29, side layout, cohen 09");
		p.setLanguage("French-IN");
		p.setPhone_num("(+91) 983 893 3937");
		p.setWebsite("http://google.com");
		p.setName("Frontline house");
		List<String> myList = new ArrayList<String>();
		myList.add("shoe park");
		myList.add("shop");
		
		p.setTypes(myList);
		
		Location l = new Location();
		l.setLat(-38.98764);
		l.setLng(33.427362);
		
		p.setLocation(l);
		
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		String res = given().queryParam("key", "qaclick123")
		.body(p)
		.when().post("/maps/api/place/add/json")
		.then().assertThat().statusCode(200).extract().response().asString();
		
		System.out.println(res);
		
	}
	

}
