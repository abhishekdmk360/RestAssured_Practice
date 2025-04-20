import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import pojo.AddPlace;
import pojo.Location;

import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.List;

public class SpecBuilder {

	public static void main(String[] args) {
		
		AddPlace place = new AddPlace();
		
		Location location = new Location();
		location.setLat(-43.383);
		location.setLng(30.983494);
		place.setLocation(location);
		
		place.setAccuracy(44);
		place.setName("Bisarjan Ghat");
		place.setPhone_number("(+91) 983 893 3937");
		place.setAddress("DF Block");
		
		List<String> types = new ArrayList<String>();
		types.add("Biswa Bangla");
		types.add("Newtown");
		place.setTypes(types);
		
		place.setWebsite("https:restassured.com");
		place.setLanguage("JAVA");
		
		RequestSpecification requestSpec = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").addQueryParam("key", "qaclick123")
				.setContentType(ContentType.JSON).build();
		ResponseSpecification responseSpec = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
		RequestSpecification request = given().log().all().spec(requestSpec).body(place);
		Response response =	request.when().post("maps/api/place/add/json")
				.then().spec(responseSpec).extract().response();
		
		String responseString = response.asString();
		System.out.print("API response : ");
		System.out.println(responseString);

	}

}
