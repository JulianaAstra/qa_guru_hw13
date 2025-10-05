package tests.petstore;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.basePath;
import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.CoreMatchers.is;
import static tests.petstore.TestData.*;

public class PetStoreTests extends TestBase {
    @Test
    @DisplayName("PUT Обновить теги питомца")
    void renewPetTagsTest() {
        TestData testData = new TestData();

        // предусловие: создать нового питомца
        given()
                .body(testData.newPet)
                .header("x-api-key", API_KEY)
                .contentType(JSON)
                .when()
                .post(baseURI + basePath)
                .then()
                .log().body()
                .statusCode(200);

        // добавить тег
        given()
                .body(testData.petWithTag)
                .header("x-api-key", API_KEY)
                .contentType(JSON)
                .log().uri()
                .when()
                .put(baseURI + basePath)
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("id", is(petId))
                .body("tags[0].id", is(petTagId))
                .body("tags[0].name", is(petTag));
    }
}
