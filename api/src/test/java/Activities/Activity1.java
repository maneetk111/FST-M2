package Activities;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

import org.testng.annotations.Test;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
public class Activity1 {
    final static String base_URI = " https://petstore.swagger.io/v2/pet";
    @Test (priority = 1)
    public void petPostRequest(){
        String reqBody = "{ \"id\": 77232, \"name\": \"Riley\", \"status\": \"alive\"}";
        Response response = given().contentType(ContentType.JSON).body(reqBody).when().post(base_URI);
        System.out.println(response.asPrettyString());
        // Assertions
        response.then().body("id", equalTo(77232));
        response.then().body("name", equalTo("Riley"));
        response.then().body("status", equalTo("alive"));
    }
    @Test (priority = 2)
    public void petGetRequest(){
        Response response = given().contentType(ContentType.JSON).when().pathParam("petId", "77232")
                .get(base_URI + "/{petId}");
        System.out.println(response.asPrettyString());
        // Assertions
        response.then().body("id", equalTo(77232));
        response.then().body("name", equalTo("Riley"));
        response.then().body("status", equalTo("alive"));
    }
    @Test (priority = 3)
    public void petDeleteRequest() {
        Response response = given().contentType(ContentType.JSON)
                .when().pathParam("petId", "77232").delete(base_URI + "/{petId}");
        System.out.println(response.asPrettyString());
        // Assertions
        response.then().statusCode(200);
        response.then().body("message", equalTo("77232"));
    }
}
