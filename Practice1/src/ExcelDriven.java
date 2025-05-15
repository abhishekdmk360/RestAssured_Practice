import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class ExcelDriven {

	@Test
	public void addBook() throws IOException
	{
		DataDriven d=new DataDriven();
		ArrayList data=d.getData("AddBook","RestAssured");
		
		
		HashMap<String, Object>  map = new HashMap<>();
		map.put("name", data.get(1));
		map.put("isbn", data.get(2));
		map.put("aisle", data.get(3));
		map.put("author", data.get(4));
		
		
		RestAssured.baseURI="http://216.10.245.166";
		Response resp=given().log().all().header("Content-Type","application/json").
		body(map).
		when().post("/Library/Addbook.php").
		then().log().all().assertThat().statusCode(200).
		extract().response();
		JsonPath js= new JsonPath(resp.asString());
		String id=js.get("ID");
		System.out.println(id);

	}

}
