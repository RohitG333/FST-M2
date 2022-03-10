package activities;

import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class Activity1 {
    // Set base URL
    final static String ROOT_URI = "https://petstore.swagger.io/v2/pet";

    @Test(priority=1)
    public void addNewPet() {
        String reqBody = "{"
            + "\"id\": 34345,"
            + "\"name\": \"Piley\","
            + " \"status\": \"alive\""
        + "}";

        Response response = 
            given().contentType(ContentType.JSON)
            .body(reqBody)
            .when().post(ROOT_URI);

        response.then().body("id", equalTo(34345));
        response.then().body("name", equalTo("Piley"));
        response.then().body("status", equalTo("alive"));
    }

    @Test(priority=2)
    public void getPetInfo() {
        Response response = 
            given().contentType(ContentType.JSON)
            .when().pathParam("petId", "34345")
            .get(ROOT_URI + "/{petId}");

        response.then().body("id", equalTo(34345));
        response.then().body("name", equalTo("Piley"));
        response.then().body("status", equalTo("alive"));
    }
    
    @Test(priority=3)
    public void deletePet() {
        Response response = 
            given().contentType(ContentType.JSON)
            .when().pathParam("petId", "34345")
            .delete(ROOT_URI + "/{petId}");

        // Assertion
        response.then().body("code", equalTo(200));
        response.then().body("message", equalTo("34345"));
    }
}
