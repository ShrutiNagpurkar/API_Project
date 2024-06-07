package test;

import org.testng.Assert;

import file.payloadFour;
import io.restassured.path.json.JsonPath;

public class ComplexJsonParseThree {

	public static void main(String[] args) {
		JsonPath js = new JsonPath(payloadFour.CoursePrice());
		
		//Print No of courses returned by API
		int count = js.getInt("courses.size()");
		System.out.println(count);
		
		//Print Purchase Amount
		int amount = js.getInt("dashboard.purchaseAmount");
		System.out.println(amount);

		//Print Title of the first course
		String firstCourse = js.getString("courses[0].title");
		System.out.println(firstCourse);
		
		//Print All course titles and their respective Prices
		for(int i=0;i<count;i++)
		{
			String title = js.getString("courses["+i+"].title");
			System.out.println(title);
			
			int price = js.getInt("courses["+i+"].price");
			System.out.println(price);
		}
		
		// Print no of copies sold by RPA Course
		for(int i=0;i<count;i++)
		{
			String title = js.getString("courses["+i+"].title");
			if(title.equalsIgnoreCase("RPA"))
			{
				System.out.println(js.getInt("courses["+i+"].copies"));
				break;
			}
		}
		
		
		//Verify if Sum of all Course prices matches with Purchase Amount
		int sum =0;
		for(int i=0;i<count;i++)
		{
			int price = js.getInt("courses["+i+"].price");
			int copies = js.getInt("courses["+i+"].copies");
			
			int amount1 = price * copies;
			System.out.println(amount1);
			sum = sum +amount1;
			System.out.println(sum);
		}
		Assert.assertEquals(amount, sum);
	}
}