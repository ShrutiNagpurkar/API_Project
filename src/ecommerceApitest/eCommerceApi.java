package ecommerceApitest;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;

public class eCommerceApi {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		RequestSpecification req = new RequestSpecBuilder()
				.setBaseUri("https://rahulshettyacademy.com")
				.setContentType(ContentType.JSON).build();
		
		LoginRequest loginRequest = new LoginRequest();
		loginRequest.setUserEmail("shruti@mailinator.com");
		loginRequest.setUserPassword("Shruti@27");
		
		//SSL Certification
		RequestSpecification reqLogin = given().relaxedHTTPSValidation().log().all()
				.spec(req).body(loginRequest);
		LoginResponse loginResponse = reqLogin.when()
				.post("/api/ecom/auth/login").then()
		.extract().response().as(LoginResponse.class);
		String token = loginResponse.getToken();
		String userId = loginResponse.getUserId();	
		
		//Add Product
		
		RequestSpecification addProductBaseReq = new RequestSpecBuilder()
				.setBaseUri("https://rahulshettyacademy.com")
				.addHeader("authorization", token).build();
		
		RequestSpecification reqAddProduct = given().log().all().spec(addProductBaseReq).param("productName", "Laptop")
		.param("productAddBy", userId).param("productCategory", "fashion")
		.param("productSubCategory", "shirts")
		.param("productPrice", "11500")
		.param("productDescription", "Lenova").param("productFor", "men")
		.multiPart("productImage",new File("C:\\Users\\HP\\OneDrive\\Pictures\\Screenshots\\path.png"));
	
		String addproductResponse = reqAddProduct.when().post("/api/ecom/product/add-product")
		.then().log().all().extract().response().asString();
	
		JsonPath js = new JsonPath(addproductResponse);
		String productId = js.getString("productId");
		String message = js.getString("message");
		
		//create order
		RequestSpecification reqCreateOrder = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addHeader("authorization", token)
				.setContentType(ContentType.JSON)
				.build();
		
		OrderDetails orderDetail = new OrderDetails();
		orderDetail.setCountry("india");
		orderDetail.setProductOrderId(productId);
		
		List<OrderDetails> orderDetailList = new ArrayList();
		orderDetailList.add(orderDetail);
		Orders orders = new Orders();
		orders.setOrders(orderDetailList);
		
		
		RequestSpecification createOrderReq = given().log().all().spec(reqCreateOrder).body(orders);
		
		String responseAddOrder = createOrderReq.when().post("/api/ecom/order/create-order").then().log().all()
				.extract().response().asString();
		System.out.println(responseAddOrder);
		
		
		//delete product
		RequestSpecification deleteProdBaseReq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addHeader("authorization", token).setContentType(ContentType.JSON).build();
		
		RequestSpecification deleteProdReq = given().log().all().spec(deleteProdBaseReq).pathParam("productId", productId);
		
		String deleteResponse = deleteProdReq.when().delete("api/ecom/product/delete-product/{productId}")
		.then().log().all().extract().response().asString();
		
		JsonPath js1 = new JsonPath(deleteResponse);
		
		Assert.assertEquals("Product Deleted Successfully", js1.get("message"));
		
		
		
		
		
		
		
		
		
	}
}