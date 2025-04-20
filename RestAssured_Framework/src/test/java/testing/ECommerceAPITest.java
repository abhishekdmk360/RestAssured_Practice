package testing;

import io.restassured.specification.RequestSpecification;
import pojo.LoginRequest;
import pojo.LoginResponse;
import pojo.OrderDetail;
import pojo.Orders;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;


public class ECommerceAPITest {

	public static void main(String[] args) {
		
		RequestSpecification requestSpec = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").setContentType(ContentType.JSON).build();
		
		LoginRequest loginRequest = new LoginRequest();
		loginRequest.setUserEmail("abhishekdmk@gmail.com");
		loginRequest.setUserPassword("Abhishek@88");
		
		RequestSpecification request = given().spec(requestSpec).body(loginRequest);
		LoginResponse loginResponse = request.when().post("api/ecom/auth/login").then().extract().response().as(LoginResponse.class);
		
		String token = loginResponse.getToken();
		String userId = loginResponse.getUserId();
		
		//Add Product
		
		RequestSpecification addProductBaseReq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").addHeader("Authorization", token).build();
		
		RequestSpecification reqAddProduct = given().log().all().spec(addProductBaseReq).param("productName", "Laptop")
				.param("productAddedBy", userId).param("productCategory", "Electronics").param("productSubCategory", "Gadgets")
				.param("productPrice", "50500").param("productDescription", "DELL").param("productFor", "Techies")
				.multiPart("productImage", new File("./DELL Laptop.png"));
		
		String addProductResponse = reqAddProduct.when().post("api/ecom/product/add-product").then().log().all().statusCode(201).extract().response().asString();
		JsonPath js = new JsonPath(addProductResponse);
		String productId = js.get("productId");
		
		//Place Order
		
		RequestSpecification placeOrderBaseReq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addHeader("Authorization", token).setContentType(ContentType.JSON).build();
		
		OrderDetail orderDetail = new OrderDetail();
		orderDetail.setCountry("India");
		orderDetail.setProductOrderedId(productId);
		
		List<OrderDetail> orderDetail_list = new ArrayList<OrderDetail>();
		orderDetail_list.add(orderDetail);
		Orders orders = new Orders();
		orders.setOrders(orderDetail_list);
		
		RequestSpecification placeOrderReq = given().log().all().spec(placeOrderBaseReq).body(orders);
		
		String responseAddOrder = placeOrderReq.when().post("api/ecom/order/create-order").then().log().all().extract().response().asString();
		
		//Delete Product
		
		RequestSpecification deleteProductBaseReq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addHeader("Authorization", token).setContentType(ContentType.JSON).build();
		
		RequestSpecification deleteProductReq = given().log().all().spec(deleteProductBaseReq).pathParam("productId", productId);
		String deleteProductResponse = deleteProductReq.when().delete("api/ecom/product/delete-product/{productId}")
				.then().log().all().extract().response().asString();
		
		JsonPath js1 = new JsonPath(deleteProductResponse);
		Assert.assertEquals(js1.get("message"), "Product Deleted Successfully");

	}

}
