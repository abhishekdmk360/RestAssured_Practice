import files.Payload;
import io.restassured.path.json.JsonPath;

public class ComplexJsonParse {

	public static void main(String[] args) {
		
		JsonPath js = new JsonPath(Payload.courseJson());
		
		//Print number of courses
		int courseCount = js.getInt("courses.size()");
		System.out.println("Courses Count = "+courseCount);
		
		//Print Purchase Amount
		int purchaseAmount = js.getInt("dashboard.purchaseAmount");
		System.out.println("Purchase Amount = "+purchaseAmount);
		
		//Print the Title of first course
		String title = js.get("courses[0].title");
		System.out.println("First Course title : "+title);
		
		//Print Title and their respective Prices for all courses
		for(int i=0;i<courseCount;i++)
		{
			System.out.println("Course Title: "+js.getString("courses["+i+"].title")+" , Course Price : "+js.getInt("courses["+i+"].price"));
		}
		
		//Print the copies count for Course "RPA"
		for(int i=0;i<courseCount;i++)
		{
			if(js.getString("courses["+i+"].title")=="RPA")
			{
				int copies = js.getInt("courses["+i+"].price");
				System.out.println("Copies = "+copies);
				break;
			}
			
		}
		
	}

}
