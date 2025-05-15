import static io.restassured.RestAssured.*;

import org.testng.Assert;

import io.restassured.path.json.JsonPath;
public class GraphQL {

	public static void main(String[] args) {
		
		//Query
		int characterId= 801, episodeId=699;
		String response = given().log().all().header("Content-Type","application/json")
				.body("{\"query\":\"query($characterId : Int!, $episodeId : Int!)\\n{\\n  \\n  character(characterId: $characterId)\\n  {\\n    name\\n    gender\\n    status\\n    id\\n  }\\n  location(locationId: 984)\\n  {\\n    name\\n    dimension\\n  }\\n  episode(episodeId: $episodeId)\\n  {\\n    name\\n    air_date\\n    episode\\n  }\\n  characters(filters:{name: \\\"Abhishek\\\"})\\n  {\\n    info\\n    {\\n      count\\n    }\\n    result\\n    {\\n      status\\n      type\\n    }\\n  }\\n  episodes(filters: {episode: \\\"GOT\\\"})\\n  {\\n    result\\n    {\\n      id\\n      name\\n      air_date\\n      episode\\n    }\\n  }\\n}\\n\",\"variables\":{\"characterId\":"+characterId+",\"episodeId\":"+episodeId+"}}")
				.when().post("https://rahulshettyacademy.com/gq/graphql")
				.then().log().all().extract().response().asString();
		System.out.println(response);
		JsonPath js = new JsonPath(response);
		String characterName = js.getString("data.character.name");
		Assert.assertEquals(characterName, "Rahul");
		
		
		//Mutation
		String newCharacterName = "Baskin Robin";
		String responseMutation = given().log().all().header("Content-Type","application/json")
				.body("{\"query\":\"mutation($locationName:String!, $characterName:String!, $episodeName:String!)\\n{\\n  createLocation(location : {name : $locationName, type: \\\"Central\\\", dimension : \\\"238\\\"})\\n  {\\n    id\\n  }\\n  \\n  createCharacter(character : {name:$characterName,type:\\\"Detective\\\",status:\\\"Alive\\\",species:\\\"Human\\\",gender:\\\"Male\\\",image:\\\".png\\\",locationId:20849,originId:20849})\\n  {\\n    id\\n  }\\n  createEpisode(episode:{name:$episodeName,air_date:\\\"2018\\\",episode:\\\"8\\\"})\\n  {\\n    id\\n  }\\n  deleteLocations(locationIds:[20851,20850])\\n  {\\n    locationsDeleted\\n  }\\n}\\n\",\"variables\":{\"locationName\":\"New Zealand\",\"characterName\":\""+newCharacterName+"\",\"episodeName\":\"GOT\"}}")
				.when().post("https://rahulshettyacademy.com/gq/graphql")
				.then().log().all().extract().response().asString();
		System.out.println(responseMutation);
	}

}
