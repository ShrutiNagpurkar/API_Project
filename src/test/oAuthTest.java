package test;

import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.testng.Assert;

import io.restassured.path.json.JsonPath;
import pojo.Api;
import pojo.GetCourse;
import pojo.WebAutomation;
public class oAuthTest {

	public static void main(String[] args)
	{
		String response = given().formParam("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
		.formParam("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
		.formParam("grant_type", "client_credentials")
		.formParam("scope", "trust")
		.when().log().all()
		.post("https://rahulshettyacademy.com/oauthapi/oauth2/resourceOwner/token").asString();
		
		System.out.println(response);
		
		JsonPath js = new JsonPath(response);
		String accessToken = js.getString("access_token");
		
		 GetCourse gc  =given().queryParam("access_token",accessToken)
		.when().log().all().get("https://rahulshettyacademy.com/oauthapi/getCourseDetails")
		.as(GetCourse.class);
		//System.out.println(gc);
		
		System.out.println(gc.getLinkedIn());
		System.out.println(gc.getInstructor());
		
		// api - title of index 1
		gc.getCourses().getApi().get(1).getCourseTitle();
		
		List<Api> apiCourses = gc.getCourses().getApi();
		for(int i=0; i<apiCourses.size();i++)
		{
			if(apiCourses.get(i).getCourseTitle().equalsIgnoreCase("SoapUI Webservices testing"))
			{
				String price1 = apiCourses.get(i).getPrice();
				System.out.println(price1);
			}
		}
		
		String[] courseTitle = {"Selenium Webdriver Java"
				, "Cypress"
				, "Protractor"};
		
		ArrayList<String> a = new ArrayList<String>();
		//print all webAutomation title using loop
		List<WebAutomation> webAutomations = gc.getCourses().getWebAutomation();
		for(int j=0; j<webAutomations.size();j++)
		{
			a.add(webAutomations.get(j).getCourseTitle());
		}
		
		List<String> expectedList = Arrays.asList(courseTitle);
		
		Assert.assertTrue(a.equals(expectedList));		
	}
}