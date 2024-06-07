package test;

import static io.restassured.RestAssured.given;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class DynamicJsonTwo {

	// content of the file to String -> content of file can convert into byte -> Byte data to String
		@Test(dataProvider="BooksData")
		public void AddBook(String isbn, String aisle) throws IOException
		{
			RestAssured.baseURI = "http://216.10.245.166";
			String resp = given().header("Content-Type","application/json")
			.body(new String(Files.readAllBytes(Paths.get("C:\\Users\\HP\\OneDrive\\Desktop\\Notes\\filepath.json"))))
					//payload.AddBook1(isbn,aisle))
			.when().post("/Library/Addbook.php")
			.then().assertThat().statusCode(200).extract().response().asString();
			
			JsonPath js = new JsonPath(resp);
			
			String id = js.get("ID");
			System.out.println(id);
			
			//deleteBook
		}
		
		//utility
		@DataProvider(name="BooksData")
		public Object[][] getData()
		{
			//creating multi dimentional array
			//array - collection of elements
			//multidimentional array = collection of arrays
			return new Object[][] {{"sxdf","234"}, {"kijnh","567"}, {"okijh","4567"}};
		}
}
