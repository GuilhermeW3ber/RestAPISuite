package restAssured;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

import org.hamcrest.Matchers;

import io.restassured.http.ContentType;

public class HTML {
	@Test
	public void searchWithHTML() {
		given()
			.log().all()
		.when()
			.delete("https://restapi.wcaquino.re/v2/users")
		.then()
			.log().all()
			.statusCode(400)
			.body("html.body.div.table.tbody.tr.size()", Matchers.is(3))
			.body("html.body.div.table.tbody.tr[1].td[2]", Matchers.is("26"))
			.appendRootPath("html.body.div.table,tbody")
			.body("tr.find{t.toString().startsWith('2')}.td[1]", is("Maria Joaquina"))
			;
	}
	
	@Test
	public void searchWithXML() {
		given()
			.log().all()
		.when()
			.delete("https://restapi.wcaquino.re/v2/users")
		.then()
			.log().all()
			.statusCode(400)
			.contentType(ContentType.HTML)
			.body(hasXPath("count(//table/tr)", is("4")))
			.body(hasXPath("//td[text()='2']/../td[2]", is("Maria Joaquina")))
			;
	}
}
