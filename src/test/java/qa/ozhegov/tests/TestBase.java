package qa.ozhegov.tests;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;

public class TestBase {

    @BeforeAll
    static void setUp(){

        RestAssured.baseURI = "https://reqres.in";
        RestAssured.basePath = "/api/users";
    }
}
