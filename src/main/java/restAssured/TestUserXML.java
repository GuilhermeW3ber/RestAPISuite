package restAssured;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import java.util.ArrayList;

import io.restassured.internal.path.xml.NodeImpl;

public class TestUserXML {
	@Test
	public void workXML() {
		given()
		.when()
			.get("https://restapi.wcaquino.me/usersXML/3")
		.then()
			.statusCode(200)
			.body("user.nome", is("Ana Julia"))
			.body("user.@id", is("3"))
			.body("user.filhos.nome.size()", is(2))
			.body("user.filhos.nome[0]", is("Zezinho"))
			.body("user.filhos.nome[1]", is("Luizinho"))
			.body("user.filhos.nome", hasItem("Luizinho"))
			.body("user.filhos.nome", hasItems("Luizinho", "Zezinho"))
			;
	}
	
	public void advancedSearchXML(){
		given()
		.when()
			.get("https://restapi.wcaquino.me/usersXML")
		.then()
			.statusCode(200)
			.body("users.user.size()", is(3))
			.body("users.user.findAll(){it.age.toInteger()<=25}.size()", is(2))
			.body("users.user.@id", hasItems("1","2","3"))
			.body("users.user.findAll{it.name.toString().contains('n')}.nome", hasItems("maria Joaquina","Ana Julia"))
			.body("users.user.salary.find{it!=null}.toDouble()", is(1234.5678d))
			.body("users.user.age.collect{it.age.toInteger()*2}", hasItems(40,50,60))
			.body("users.user.name.findAll{it.name.toString().startsWith('Maria')}.collect{it.toString().toUpperCase}", is("MARIA JOAQUINA"))
			;
	}
	
	public void xmlAndJAVASearch() {
		ArrayList<NodeImpl> nomes= given()
				.when()
				.get("https://restapi.wcaquino.me/usersXML")
			.then()
				.statusCode(200)
				.extract().path("users.user.findAll{it.toString().contains('n')}")
				;
		Assert.assertEquals(1, nomes.size());
		Assert.assertEquals("Maria Joaquina".toUpperCase(), nomes.get(0).toString().toUpperCase());
		Assert.assertTrue("ANA JULIA".equalsIgnoreCase(nomes.get(1).toString()));
	}
	
	public void advancedJAVASearchWithXPath(){
		given()
		.when()
			.get("https://restapi.wcaquino.me/usersXML")
		.then()
			.statusCode(200)
			.body(hasXPath("count(/users/user)", is("3")))
			.body(hasXPath("/users/user[@id='1']"))
			.body(hasXPath("//users[@id='2']"))
			.body(hasXPath("//name[text()='Luizinho']/../../name", is("Ana Julia")))
			.body(hasXPath("//name[text()='Ana julia']/following-sibling::filhos", allOf(containsString("Zezinho"), containsString("Luizinho"))))
			.body(hasXPath("/users/user/name", is("João Silva")))
			.body(hasXPath("//name", is("João Silva")))
			.body(hasXPath("/users/user[2]/name", is("Maria Joaquina")))
			.body(hasXPath("/users/user[last()]/name", is("Ana Julia")))
			.body(hasXPath("count(/users/user[2]/name[contains(., 'n')])", is("2")))
			.body(hasXPath("//user[age<24]/name", is("Ana Julia")))
			.body(hasXPath("//user[age < 20 and age < 30]/name", is("Maria Joaquina")))
			.body(hasXPath("//user[age < 20][age < 30]/name", is("Maria Joaquina")))
			;
	}
}
