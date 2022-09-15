package restAssured;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class TestPublicAPI {
	@Test
	public void acessSWAAPI() {
	given()
		.log().all()
	.when()
		.get("https://swapi.co/api/people/1")
	.then()
		.log().all()
		.statusCode(200)
		.body("name", is("Luke Skywalker"))
		;
	}
}
