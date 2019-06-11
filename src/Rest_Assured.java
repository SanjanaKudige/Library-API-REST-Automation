import static io.restassured.RestAssured.given;
import Files.resources;
import Files.payLoad;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class Rest_Assured {
	Properties prop = new Properties();

	@BeforeTest
	public void getData() throws IOException {
		FileInputStream fis = new FileInputStream(
				"/Users/balajianoopgupta/Documents/workspace/JSONHandling/src/Files/env.properties");
		prop.load(fis);

	}

	@Test(dataProvider = "BookData")
	public void addBook(String isbn, String aisle)

	{

		RestAssured.baseURI = prop.getProperty("HOST");

		Response res = given().header("Content-Type", "application/json").
		// queryParam("key", prop.getProperty("KEY")).

				body(payLoad.getPostData(isbn, aisle)).

				when().

				post(resources.placePostData()).

				then().assertThat().statusCode(200).and().contentType(ContentType.JSON).and().

				extract().response();

		// JsonPath j = new JsonPath();

		String resStr = res.asString();
		JsonPath jpath = new JsonPath(resStr);
		String place_id = jpath.get("ID");
		System.out.println("Place ID is : " + place_id);
		// Create a place =response (place id)

		// delete Place = (Request - Place id)

	}

	@DataProvider(name = "BookData")
	public Object[][] getBookData() {

		return new Object[][] { { "SanjanaKudige", "1234" }, { "Balaji", "2345" } };
	}

}
