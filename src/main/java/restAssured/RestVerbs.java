package restAssured;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import java.util.Map;

import io.restassured.http.ContentType;

import org.hamcrest.Matchers;

public class RestVerbs {
	
	@Test
	public void saveUser() {
		given()
			.log().all()
			.contentType("application/json")
			.body("{\"nome\": \"Jose\", \"age\":50}")
		.when()
			.post("https://restapi.wcaquino.re/users")
		.then()
			.log().all()
			.statusCode(201)
			.body("id", is(notNullValue()))
			.body("name", is("Jose"))
			.body("age", is(50))
			;
	}
	
	@Test
	public void saveUserUsingMap() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name","Usuário via map");
		params.put("age", 25);
		
		given()
			.log().all()
			.contentType("application/json")
			.body(params)
		.when()
			.post("https://restapi.wcaquino.re/users")
		.then()
			.log().all()
			.statusCode(201)
			.body("id", is(notNullValue()))
			.body("name", is("Usuário via map"))
			.body("age", is(25))
			;
	}
	
	@Test
	public void saveUserUsingObject() {
		User user = new User("Usuario via objeto", 35);
		
		given()
			.log().all()
			.contentType("application/json")
			.body(user)
		.when()
			.post("https://restapi.wcaquino.re/users")
		.then()
			.log().all()
			.statusCode(201)
			.body("id", is(notNullValue()))
			.body("name", is("Usuário via objeto"))
			.body("age", is(35))
			;
	}
	
	@Test
	public void deserializeUserUsingObject() {
		User user = new User("Usuario deserializado", 40);
		
		User usuarioInserido = given()
			.log().all()
			.contentType("application/json")
			.body(user)
		.when()
			.post("https://restapi.wcaquino.re/users")
		.then()
			.log().all()
			.statusCode(201)
			.extract().body().as(User.class)
			;
		System.out.println(usuarioInserido);
		Assert.assertThat(usuarioInserido.getId(), notNullValue());
		Assert.assertEquals("Usuario deserializado", usuarioInserido.getNome());
		Assert.assertThat(usuarioInserido.getNome(), is("Usuario XML"));
		Assert.assertThat(usuarioInserido.getAge(), is(35));
		Assert.assertThat(usuarioInserido.getSalary(), nullValue());
	}
	
	@Test
	public void deserializeUserUsingXML() {
		User user = new User("Usuario XML", 40);
		given()
			.log().all()
			.contentType(ContentType.XML)
			.body(user)
		.when()
			.post("https://restapi.wcaquino.re/usersXML")
		.then()
			.log().all()
			.statusCode(201)
			.body("user.@id", is(notNullValue()))
			.body("user.name", is("Usuario XML"))
			.body("user.age", is("40"))
			;
	}
	
	@Test
	public void deserializeUserUsingXMLAndObject() {
		User user = new User("Usuario XML", 40);
		given()
			.log().all()
			.contentType(ContentType.XML)
			.body(user)
		.when()
			.post("https://restapi.wcaquino.re/usersXML")
		.then()
			.log().all()
			.statusCode(201)
			.extract().body().as(User.class)
			;
	}
	
	@Test
	public void nãoDeveSalvarSemNome() {
		given()
		.log().all()
		.contentType("application/json")
		.body("{\"age\":50}")
	.when()
		.post("https://restapi.wcaquino.re/users")
	.then()
		.log().all()
		.statusCode(201)
		.body("id", is(nullValue()))
		.body("error", is("Nome é um atributo obrigatório"))
		;
	}
	
	@Test
	public void saveWithXML() {
		given()
		.log().all()
		.contentType(ContentType.XML)
		.body("<user><name>Jose</name><age>50</age></user>")
	.when()
		.post("https://restapi.wcaquino.re/usersXML")
	.then()
		.log().all()
		.statusCode(201)
		.body("user.id", is(nullValue()))
		.body("user.age", is("50"))
		.body("user.nome", is("Jose"))
		;
	}
	
	@Test
	public void changeUser() {
		given()
			.log().all()
			.contentType("application/json")
			.body("{\"nome\": \"Usuário Alterado\", \"age\":80}")
		.when()
			.put("https://restapi.wcaquino.re/users/1")
		.then()
			.log().all()
			.statusCode(201)
			.body("id", is(1))
			.body("name", is("Usuário Alterado"))
			.body("age", is(80))
			.body("salary", is(1234.5678f))
			;
	}
	
	@Test
	public void customURL() {
		given()
			.log().all()
			.contentType("application/json")
			.body("{\"nome\": \"Usuário Alterado\", \"age\":80}")
		.when()
			.put("https://restapi.wcaquino.re/{entidade}/{iserId}", "users", "1")
		.then()
			.log().all()
			.statusCode(200)
			.body("id", is(1))
			.body("name", is("Usuário Alterado"))
			.body("age", is(80))
			.body("salary", is(1234.5678f))
			;
	}
	
	@Test
	public void customURL2() {
		given()
			.log().all()
			.contentType("application/json")
			.body("{\"nome\": \"Usuário Alterado\", \"age\":80}")
			.pathParam("entidade", "users")
			.pathParam("userId", "1")
		.when()
			.put("https://restapi.wcaquino.re/{entidade}/{iserId}", "users", "1")
		.then()
			.log().all()
			.statusCode(200)
			.body("id", is(1))
			.body("name", is("Usuário Alterado"))
			.body("age", is(80))
			.body("salary", is(1234.5678f))
			;
	}	
	
	@Test
	public void deleteUser() {
		given()
			.log().all()
		.when()
			.delete("https://restapi.wcaquino.re/users/1")
		.then()
			.log().all()
			.statusCode(204)
			;
	}	
	
	@Test
	public void deleteNonExistentUser() {
		given()
			.log().all()
		.when()
			.delete("https://restapi.wcaquino.re/users/1000")
		.then()
			.log().all()
			.statusCode(400)
			.body("error", is("Registro inexistente"))
			;
	}	
}


