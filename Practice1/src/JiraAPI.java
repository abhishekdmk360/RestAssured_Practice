import io.restassured.RestAssured;import io.restassured.path.json.JsonPath;
import static io.restassured.RestAssured.*;
import java.io.File;

public class JiraAPI {

	public static void main(String[] args) {
		RestAssured.baseURI="https://abhishekdmk360.atlassian.net/";
		String createIssueResponse 	= given()
				.header("Content-Type","application/json")
				.header("Authorization","Basic YWJoaXNoZWtkbWszNjBAZ21haWwuY29tOkFUQVRUM3hGZkdGMGQ1TFJPYXNNZmVJM0RYcEh2Unp3cVlhc1V6UlJtZjFIV2U5ZnNhQUg0ZGJXdm9OQ21YQTdrRm8wcGVtaF9ySFNQczBZb0ktXzVQQVF0aWdITk01Y1pyUDY0RVFMaUtvekllX080ajNsUFkxREp4dG56X19TZVJkdUZqT2pscnlOR3pTRDlYUnA2bFVhZlhLZG1LdExvR2oyVzdZQkRMTXhaa3Z5dGZHVXZ1TT05M0NDNjk2QQ==")
				.body("{\r\n"
						+ "    \"fields\": {\r\n"
						+ "       \"project\":\r\n"
						+ "       {\r\n"
						+ "          \"key\": \"SCRUM\"\r\n"
						+ "       },\r\n"
						+ "       \"summary\": \"Automation Create BUG using API\",\r\n"
						+ "       \"issuetype\": {\r\n"
						+ "          \"name\": \"Bug\"\r\n"
						+ "       }\r\n"
						+ "   }\r\n"
						+ "}")
				.log().all()
				.post("rest/api/3/issue").then().log().all().assertThat().statusCode(201).contentType("application/json")
				.extract().response().asString();
		
		JsonPath js = new JsonPath(createIssueResponse);
		String issueId = js.getString("id");
		System.out.println(issueId);
		
		given()
		.pathParam("key", issueId)
		.header("X-Atlassian-Token","no-check")
		.header("Authorization","Basic YWJoaXNoZWtkbWszNjBAZ21haWwuY29tOkFUQVRUM3hGZkdGMGQ1TFJPYXNNZmVJM0RYcEh2Unp3cVlhc1V6UlJtZjFIV2U5ZnNhQUg0ZGJXdm9OQ21YQTdrRm8wcGVtaF9ySFNQczBZb0ktXzVQQVF0aWdITk01Y1pyUDY0RVFMaUtvekllX080ajNsUFkxREp4dG56X19TZVJkdUZqT2pscnlOR3pTRDlYUnA2bFVhZlhLZG1LdExvR2oyVzdZQkRMTXhaa3Z5dGZHVXZ1TT05M0NDNjk2QQ==")
		.multiPart("file",new File("C:\\Users\\Abhishek Kumar\\Pictures\\Screenshots\\Screenshot (82).png")).log().all()
		.post("rest/api/3/issue/{key}/attachments").then().log().all().assertThat().statusCode(200);

	}

}
