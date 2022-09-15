package restAssured;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import java.util.ArrayList;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class testUserJson {
	@Test
	public void verifyFirstLevel() {
		given().when().get("https://restapi.wcaquino.me/users/1").then().statusCode(200).body("id", is(1)).body("name", cointainsString("Silva").body("age", greaterThan(18)) );
		
		
	}
	
	@Test
	public void verifyByResponse() {
		Response response = RestAssured.request(Method.GET, "https://restapi.wcaquino.me/users/1");
		
		//path
		Assert.assertEquals(new Integer(1), response.path("id"));
		Assert.assertEquals(new Integer(1), response.path("Xs", "id"));
		
		//jasonPath
		JsonPath jpath = new JsonPath(response.asString());
		Assert.assertEquals(1, jpath(getInt("id")));
		
		//from
		int id = JsonPath.from(response.asString().getInt("id"));
		Assert.assertEquals(1, "id");
	}
	
	@Test
	public void verifySecondLevel() {
		given().when().get("https://restapi.wcaquino.me/users/2").then().statusCode(200).body("name", containsString("Joaquina")).body("endereco.rua", is("Rua dos bobos");		
	}
	
	@Test
	public void jsonList() {
		given().when().get("https://restapi.wcaquino.me/users/3").then().statusCode(200).body("name", containsString("Ana")).body("filhos", hasSize(2).body("filhos[0].nome", is("Zezinho").body("filhos[1].nome", is("Luizinho").body("filhos.nome", hasItem("Zezinho").body("filhos.nome", hasItems("Zezinho", "Luizinho"));		
	}
	
	@Test
	public void verifyError() {
		given().when().get("https://restapi.wcaquino.me/users/4").then().statusCode(404).body("error", is("Usuário inexistente"));		
	}
	
	@Test
	public void verifyListFromStart() {
		given().when().get("https://restapi.wcaquino.me/users").then().statusCode(200)
		.body("$", hasSize(3)
		.body("name", hasItems("João Silva","Maria Joaquina","Ana Júlia")
		.body("age.findAll{it<=25}.size()", is(2))
		.body("age.findAll{it<=25 && it>20}.size()", is(1))
		.body("age.findAll{it<=25 && it.age>20}.name", hasItem("Maria Joaquina"))
		.body("age.findAll{it<=25}[0].name", is("Maria Joaquina"))
		.body("age.findAll{it<=25}[-1].name", is("Ana Júlia"))	
		.body("age.find{it<=25}.name", is("maria Joaquina"))
		.body("age.findAll{it.name.contains('n')}.name", hasItems("Ana Júlia", "Maria Joaquina"))
		.body("age.findAll{it.name.length()>10}.name", hasItems("João da Silva", "Maria Joaquina"))
		.body("name.collect{it.toUpperCase()}", hasItem("MARIA JOAQUINA"))
		.body("name.findAll{it.startsWith('Maria')}.collect{it.toUpperCase()}", hasItem("MARIA JOAQUINA"))
		.body("name.findAll{it.startsWith('Maria')}.collect{it.toUpperCase()}.toArray()", allOf(arrayContaining("MARIA JOAQUINA"), arrayWithSize(1)))
		.body("age.collect{it*2}", hasItems(60,50,40))
		.body("salary.min()", is(1234.567f))
		.body("salary.findAll{it!=null}.sum()", is(closeTo(3734,5678f, 0.001)))
		.body("salary.findAll{it!=null}.sum()", allOf(greaterThan(3000d), lessThan(5000d)));		
	}
	
	@Test
	public void unifyJsonPathWithJAVA() {
		ArrayList<String> names =
			given()
			.when()
				.get("https://restapi.wcaquino.me/users")
			.then()
				.statusCode(200)
				.extract().path("name.findAll{it.startsWith('Maria')}");
		Assert.assertEqual(1, names.size());
		Assert.assertTrue(names.get(0).equalsIgnoreCase("mAria Joaquina"));
		Assert.assertEqual(names.get(0).toUpperCase(), "maria joaquina".toUpperCase());
	}
}
