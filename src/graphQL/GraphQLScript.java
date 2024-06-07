package graphQL;

import static io.restassured.RestAssured.*;

import org.testng.Assert;

import io.restassured.path.json.JsonPath;

public class GraphQLScript {

	public static void main(String[] args) {
		
		
		//Query 
		
		int characterId = 14;
		
		String response = given().log().all().header("Content-Type","application/json")
		.body("{\"query\":\"query($characterId : Int!,$episodeId: Int!)\\n{\\n  \\n  character(characterId : $characterId)\\n  {\\n    name\\n    gender\\n    id\\n    status\\n  \\n  }\\n  location(locationId:8)\\n  {\\n    name\\n    dimension\\n  }\\n  episode(episodeId:$episodeId)\\n  {\\n    name\\n    air_date\\n    episode\\n  }\\n  \\n}\\n\",\"variables\":{\""+characterId+"\":7136,\"episodeId\":5158}}")
		.when().post("https://rahulshettyacademy.com/gq/graphql")
		.then().extract().response().asString();
		
		System.out.println(response);
		
		JsonPath js = new JsonPath(response);
		String characterName = js.getString("data.character.name");
		Assert.assertEquals(characterName,"robin");
		
		
		//Mutation

		String newCharacter = "robin";
		String mutationResponse = given().log().all().header("Content-Type","application/json")
		.body("{\"query\":\"mutation($locationName:String!,$characterName:String!,$episodeName:String!)\\n{\\n  createLocation(location : {name: $locationName,type: \\\"South\\\", dimension:\\\"234\\\"})\\n{\\n  id\\n}\\n  createCharacter(character:{name: $characterName,type:\\\"Macho\\\",status:\\\"dead\\\",species:\\\"fantasy\\\",gender:\\\"male\\\",image:\\\"png\\\",originId:7131,locationId:7131})\\n{\\n  id\\n}\\n  createEpisode(episode:{name:$episodeName,air_date:\\\"as\\\",episode:\\\"hulu\\\"})\\n{\\n  id\\n}\\n  deleteLocations(locationIds:[7137])\\n  {\\n    locationsDeleted\\n  }\\n}\",\"variables\":{\"locationName\":\"Newzealand\",\"characterName\":\""+newCharacter+"\"\",\"episodeName\":\"Manifest\"}}")
		.when().post("https://rahulshettyacademy.com/gq/graphql")
		.then().extract().response().asString();
		
		System.out.println(mutationResponse);
		
	

	}

}
