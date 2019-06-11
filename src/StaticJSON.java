


import static io.restassured.RestAssured.given;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import Files.resources;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class StaticJSON {
	Properties prop = new Properties();

	@BeforeTest
	public void getData() throws IOException {
		FileInputStream fis = new FileInputStream(
				"/Users/balajianoopgupta/Documents/workspace/JSONHandling/src/Files/env.properties");
		prop.load(fis);

	}

	@Test
	public void addBook() throws IOException

	{

		RestAssured.baseURI = prop.getProperty("HOST");
		System.out.println("Str = " +GenerateStringFromResource(
				"/Users/balajianoopgupta/Desktop/SanjanaKudige/Udemy Rest API Testing/data.json"));

		Response res = given().header("Content-Type", "application/json").
		// queryParam("key", prop.getProperty("KEY")).

				body(GenerateStringFromResource(
						"/Users/balajianoopgupta/Desktop/SanjanaKudige/Udemy Rest API Testing/data.json"))
				.

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

	public static String GenerateStringFromResource(String path) throws IOException {

		return new String(Files.readAllBytes(Paths.get(path)));

	}
}
