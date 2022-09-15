package restAssured;

import static io.restassured.RestAssured.given;

import java.io.File;

import org.xml.sax.SAXParseException;

import io.restassured.matcher.RestAssuredMatchers;

public class SchemaTest {
	@Test
	public void certiftSchemaXML() {
		
		given()
			.log().all()
		.when()
			.get("https://restapi.wcaquino.me/usersXML")
		.then()
			.log().all()
			.statusCode(200) 
			.body(RestAssuredMatchers.matchesXsdInClasspath("users.xsd"))
			;
		
	}
	
	@Test(expected=SAXParseException.class)
	public void invalidatingSchema() {	
		given()
			.log().all()
		.when()
			.get("https://restapi.wcaquino.me/usersXML")
		.then()
			.log().all()
			.statusCode(200) 
			.body(RestAssuredMatchers.matchesXsdInClasspath("users.xsd"))
			;
	}
	
	@Test
	public void certiftSchemaJson() {
		
		given()
			.log().all()
		.when()
			.get("https://restapi.wcaquino.me/usersXML")
		.then()
			.log().all()
			.statusCode(200) 
			.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("users.json"))
			;
		
	}
	
}
