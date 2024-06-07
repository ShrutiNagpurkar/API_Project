package test;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

import java.io.File;

import org.testng.Assert;

import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;

public class JiraTestOne {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		RestAssured.baseURI = "http://localhost:8080";
		
		//Login Scenario
		SessionFilter session = new SessionFilter();
		//ready to listen if new session is created
		String response = given().log().all().header("Content-Type","application/json")
		.body("{ \"username\": \"nagpurkars05\", \"password\": \"Unnati@27\" }")
		.filter(session)
		.when().post("/rest/auth/1/session")
		.then().log().all().assertThat().statusCode(200).extract().response().asString();
		
		//may not worry about creating a jsonPath passing and passing into variable
		
		//add comment
		
		String com = "hello, I am adding comment";
		String addCommentResponse = given().log().all().header("Content-Type","application/json").pathParam("id", "10006")
		.body("{\r\n"
				+ "    \"body\": \""+com+"\",\r\n"
				+ "    \"visibility\": {\r\n"
				+ "        \"type\": \"role\",\r\n"
				+ "        \"value\": \"Administrators\"\r\n"
				+ "    }\r\n"
				+ "}")
		.filter(session)
		.when().post("/rest/api/2/issue/{id}/comment")
		.then().log().all().assertThat().statusCode(201).extract().response().asString();
		
		JsonPath js = new JsonPath(addCommentResponse);
		String commentId = js.get("id");
		
		//add attachment
		given().log().all().header("X-Atlassian-Token","no-check")
		.filter(session).pathParam("id", "10006")
		.header("Content-Type","multipart/form-data")
		.multiPart("file",new File("C:\\Users\\HP\\eclipse-workspace\\APIDummy\\src\\test\\jira.txt"))
		.when().post("/rest/api/2/issue/{id}/attachments")
		.then().log().all().assertThat().statusCode(200);
		
		//get issue
		String issueDetails = given().filter(session)
				.queryParam("fields", "comment")
				.pathParam("id", "10006").log().all()
		.when().get("/rest/api/2/issue/{id}")
		.then().log().all().extract().response().asString();
		System.out.println(issueDetails);
		
		JsonPath js1 = new JsonPath(issueDetails);
		int comments = js1.getInt("fields.comment.comments.size()");
		
		for(int i=0;i<comments;i++)
		{
			String commentIdIssue = js1.getString("fields.comment.comments["+i+"].id").toString();
			if(commentIdIssue.equalsIgnoreCase(commentId))
			{
			String message = js1.get("fields.comment.comments["+i+"].body").toString();
			Assert.assertEquals(com,message );
			}
		}
		
	}
}