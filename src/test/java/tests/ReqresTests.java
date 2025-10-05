package tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.*;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.CoreMatchers.is;

public class ReqresTests extends TestBase {
    @Test
    @DisplayName("Получить список всех пользователей")
    void getUsersTest() {
      given()
              .header("x-api-key", API_KEY)
              .queryParam("page", 1)
              .queryParam("per_page", 12)
      .when()
              .get(baseURI + basePath + "/users")
      .then()
              .statusCode(200)
              .assertThat().body(matchesJsonSchemaInClasspath("schemas/users-schema.json"))
              .body("page", is(1))
              .body("per_page", is(12))
              .body("total", is(12))
              .body("total_pages", is(1));
    }
}
