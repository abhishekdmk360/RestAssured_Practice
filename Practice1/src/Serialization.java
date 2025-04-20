import io.restassured.RestAssured;
import io.restassured.response.Response;
import pojo.AddPlace;
import pojo.Location;

import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.List;

public class Serialization {

	public static void main(String[] args) {
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		
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
		
		Response response = given().log().all().queryParam("key", "qaclick123")
		.body(place)
		.when().post("maps/api/place/add/json")
		.then().assertThat().statusCode(200).extract().response();
		
		String responseString = response.asString();
		System.out.print("API response : ");
		System.out.println(responseString);

	}

}
