package restAssured;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import java.io.File;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

public class FileTest {
	
	@Test
	public void sendFile() {
		
		given()
			.log().all()
			.multiPart("arquivo", new File("src/main/resources/user.pdf"))
		.when()
			.post("https://restapi.wcaquino.me/upload")
		.then()
			.log().all()
			.statusCode(404) //Deve ser 400
			.body("name", is("Arquivo não enviado"))
			;
		
	}
	
	@Test
	public void uploadFile() {
		
		given()
			.log().all()
			.multiPart("arquivo", new File("src/main/resources/user.pdf"))
		.when()
			.post("https://restapi.wcaquino.me/upload")
		.then()
			.log().all()
			.statusCode(200)
			.body("name", is("users.pdf"))
			;
		
	}
}
