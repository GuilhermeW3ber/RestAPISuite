package restAssured;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import static io.restassured.RestAssured.given;

import static io.restassured.RestAssured.get;

import java.sql.Array;
import java.util.Arrays;
import java.util.List;

import org.hamcrest.Matchers;

public class FirstAPITest {
	
	@Test
	public void FirstAPI() {
		Response response=RestAssured.request(Method.GET, "http://restapi.wcaquino.me:80/ola");
		Assert.assertTrue(response.getBody().asString().equals("Ola Mundo!"));
		Assert.assertTrue(response.statusCode()==200);
		Assert.assertTrue("O status code deveria ser 200", response.statusCode()==200);
		Assert.assertEquals(200, response.statusCode());
		
		ValidatableResponse validation = response.then();
		validation.statusCode(200);
	}
	
	@Test
	public void otherMethodsRestAssured() {
		Response response=RestAssured.request(Method.GET, "http://restapi.wcaquino.me:80/ola");
		ValidatableResponse validation= response.then();
		validation.statusCode(200);
		
		get("http://restapi.wcaquino.me:80/ola").then().statusCode(200);
		
		given()
		.when()
			.get()
		.then()
			.statusCode(200);
	}
	
	@Test
	public void hamcrest() {
		Assert.assertThat("Maria",Matchers.is("Maria"));
		Assert.assertThat("128",Matchers.is(Integer.class));
		Assert.assertThat("128b",Matchers.is(Double.class));
		Assert.assertThat("128",Matchers.greaterThan(120));
		Assert.assertThat("128",Matchers.lessThan(140));
		
		List<Integer> impares= Arrays.asList(1,3,5,7,9);
		assertThat(impares,hasSize(5));
		assertThat(impares,contains(1,3,5,7,9));
		assertThat(impares,containsInAnyOrder(1,3,5,9,7));
		assertThat(impares,hasItem(1));
		assertThat(impares,hasItems(1,2,5));
		
		Assert.assertThat("Maria",isNot("Joao"));
		Assert.assertThat("Maria",not("Joao"));
		Assert.assertThat("Joaquina",anyOf(is("Maria"), is("Joaquina")));
		Assert.assertThat("Joaquina",allOf(startsWith("Joa"), endsWith("ina"), containsString("qui")));
		
		
	}
	
	@Test
	public void validateBody() {
		given().when().get("http://restapi.wcaquino.me:80/ola").then().statusCode(200).body(is("ola Mundo!")).body(containsString("Mundo").body(is(not(nullValue()))));
	}
	
	
}
