package restAssured;

import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;
import static org.hamcrest.Matchers.is;
public class DataShip {
	
	@Test
	public void sendByQuery() {
		given()
			.log().all()
		.when()
			.get("https://restapi.wcaquino.me/v2/users?formt=xml")
		.then()
			.log().all()
			.statusCode(200)
			.contentType(ContentType.JSON)
		;
		
	}
	
	@Test
	public void sendByQueryWithParam() {
		given()
			.log().all()
			.queryParam("format", "xml")
			.queryParam("outra", "coisa")
		.when()
			.get("https://restapi.wcaquino.me/v2/users?formt=xml")
		.then()
			.log().all()
			.statusCode(200)
			.contentType(ContentType.XML)
			.contentType(containsString("utf-8"))
		;
		
	}
	
	@Test
	public void sendByQueryWithHeader() {
		given()
			.log().all()
			.accept(ContentType.XML)
		.when()
			.get("https://restapi.wcaquino.me/v2/users?formt=xml")
		.then()
			.log().all()
			.statusCode(200)
			.contentType(ContentType.XML)
		;
		
	}
	
	@Test
	public void sendByQueryWithXPath() {
		given()
			.log().all()
			.accept(ContentType.XML)
		.when()
			.get("https://restapi.wcaquino.me/v2/users?formt=clean")
		.then()
			.log().all()
			.statusCode(200)
			.contentType(ContentType.XML)
			.body(hasXPath("count(//table/tr)", is("4")))
			.body(hasXPath("//td[text()='2']", is("Maria Joaquina")))
		;
		
	}


}
