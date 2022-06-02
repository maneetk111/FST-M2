package Activities;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class Activity2 {
    final static String base_URI = "https://petstore.swagger.io/v2/user";
    @Test (priority = 1)
    public void postUserRequest() throws Exception {
        File jsonFile = new File("src/main/resources/Activity2InputPost.json");
        FileInputStream fileinput = new FileInputStream(jsonFile);
       // byte[] bytes = new byte[(int) jsonFile.length()];
      //  fileinput.read(bytes);
        String reqBody = new String(fileinput.readAllBytes());
        System.out.println(reqBody);
        Response response = given().contentType(ContentType.JSON).body(reqBody).when().post(base_URI);
        System.out.println(response.asPrettyString());
        fileinput.close();
        response.then().statusCode(200);
        response.then().body("message", equalTo("9955"));
    }
    @Test (priority = 2)
    public void getUserRequest() throws Exception {
        Response response = given().contentType(ContentType.JSON)
                .when().pathParam("username", "casjust").get(base_URI + "/{username}");
        String respBody = response.asPrettyString();
        System.out.println(respBody);
        response.then().body("id", equalTo(9955));
        response.then().body("username", equalTo("casjust"));
        response.then().body("firstName", equalTo("Justin"));
        response.then().body("lastName", equalTo("Case"));
        response.then().body("email", equalTo("justincase@mail.com"));
        response.then().body("password", equalTo("password123"));
        response.then().body("phone", equalTo("9812763450"));
        File respJsonfile = new File("src/main/resources/Activity2GetRequest.json");
        try{
            respJsonfile.createNewFile();
            FileWriter writer = new FileWriter(respJsonfile.getPath());
            writer.write(respBody);
            writer.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    @Test (priority = 3)
    public void deleteUserRequest(){
        Response response = given().contentType(ContentType.JSON).
                when().pathParam("username", "casjust").delete(base_URI + "/{username}");
        System.out.println(response.asPrettyString());
        response.then().statusCode(200);
        response.then().body("message", equalTo("casjust"));
    }
}
