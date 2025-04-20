import static io.restassured.RestAssured.given;

import java.util.ArrayList;

import java.util.Arrays;

import java.util.List;

import io.restassured.parsing.Parser;

import io.restassured.path.json.JsonPath;

import io.restassured.response.Response;

import io.restassured.response.ResponseBody;
import pojo.API;
import pojo.GetCourse;

public class PojoExample {

	public static void main(String[] args) throws InterruptedException {

// TODO Auto-generated method stub

		String response =

				given().log().all()

						.formParams("client_id",
								"692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")

						.formParams("client_secret", "erZOWM9g3UtwNRj340YYaK_W")

						.formParams("grant_type", "client_credentials")

						.formParams("scope", "trust")

						.when()

						.post("https://rahulshettyacademy.com/oauthapi/oauth2/resourceOwner/token").asString();

		System.out.println(response);

		JsonPath jsonPath = new JsonPath(response);

		String accessToken = jsonPath.getString("access_token");

		System.out.println(accessToken);

		GetCourse gc = given()

				.queryParams("access_token", accessToken)

				.when()

				.get("https://rahulshettyacademy.com/oauthapi/getCourseDetails")

				.as(GetCourse.class);
		
		System.out.println(gc.getLinkedIn());
		
		System.out.println(gc.getCourses().getApi().get(1).getCourseTitle());
		
		List<API> getAPICourses = gc.getCourses().getApi();
		for(int i=0;i<getAPICourses.size();i++)
		{
			if(getAPICourses.get(i).getCourseTitle().equalsIgnoreCase("SoapUI Webservices testing"))
				System.out.println(getAPICourses.get(i).getPrice());
		}

	}

}
