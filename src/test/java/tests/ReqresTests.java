package tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.*;
import static io.restassured.http.ContentType.JSON;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.CoreMatchers.is;

public class ReqresTests extends TestBase {
    @Test
    @DisplayName("Получить список всех пользователей")
    void getUsersTest() {
      given()
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

    @Test
    @DisplayName("Добавить нового пользователя")
    void addNewUser() {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode user = objectMapper.createObjectNode();
        TestData testData = new TestData();

        user.put("name", testData.userName);
        user.put("job", testData.job);

        given()
                .header("x-api-key",API_KEY)
                .contentType(JSON)
                .body(user)
        .when()
                .post(baseURI + basePath + "/users")
        .then()
                .log().body()
                .statusCode(201)
                .body(matchesJsonSchemaInClasspath("schemas/user-schema.json"))
                .body("name", is(testData.userName))
                .body("job", is(testData.job));
    }
}
