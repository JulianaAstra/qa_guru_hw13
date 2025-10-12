package tests.petstore;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static tests.petstore.TestData.*;

public class PetStoreTests extends TestBase {
    @Test
    @DisplayName("GET успешный логин")
    void loginPetStore() {
        TestData testData = new TestData();
        given()
                .header("x-api-key", API_KEY)
                .queryParam("username", testData.userName)
                .queryParam("password", testData.userPassword)
        .when()
                .get("/user/login")
        .then()
                .statusCode(200).body("message", containsString("logged in user session"));
    }

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
                .post("/pet")
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
                .put("/pet")
        .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("id", is(petId))
                .body("tags[0].id", is(petTagId))
                .body("tags[0].name", is(petTag));
    }

    @Test
    @DisplayName("DELETE Удалить несуществующего питомца")
    void deletePetTest() {
        given()
                .header("x-api-key", API_KEY)
                .log().uri()
        .when()
                .delete("/pet/" + notExistPetId)
        .then()
                .statusCode(404);
    }
}
