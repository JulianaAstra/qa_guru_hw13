package tests.reqres;

import org.junit.jupiter.api.BeforeAll;
import static io.restassured.RestAssured.basePath;
import static io.restassured.RestAssured.baseURI;

public class TestBase {
    public final String API_KEY = "reqres-free-v1";

    @BeforeAll
    static void setUp() {
        baseURI = "https://reqres.in";
        basePath = "/api/users";
    }
}
