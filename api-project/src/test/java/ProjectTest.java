import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
public class ProjectTest {
    RequestSpecification reqSpec;
    String sshKey="ssh-rsa <sshkey value>;
    int id;
    @BeforeClass
    public void setUp(){
        reqSpec = new RequestSpecBuilder().setContentType(ContentType.JSON)
                .addHeader("Authorization", "token ghp_U0PA<tokenvalue>")
                .setBaseUri("https://api.github.com").build();
    }
    @Test(priority=1)
    public void postApiRequest(){
        String reqBody = "{"
                + "\"title\": \"TestAPIKey\","
                + "\"key\": \""+sshKey +"\""
                + "}";
        Response response = given().spec(reqSpec).body(reqBody).when().request("POST","/user/keys");
        System.out.println(response.asPrettyString());
        id = response.then().extract().path("id");
        System.out.println(id);
        response.then().statusCode(201);
    }

    @Test(priority=2)
    public void getSshKeyRequest() {
        Response response =given().spec(reqSpec).get("/user/keys");
        String respBody = response.getBody().asPrettyString();
        System.out.println(respBody);
        Reporter.log(respBody);
    }
    @Test(priority=3)
    public void DeleteKeyRequest() {
        Response response =given().spec(reqSpec).pathParam("keyId",id).delete("user/keys/{keyId}");
        String respBody = response.getBody().asPrettyString();
        System.out.println(respBody);
        Reporter.log(respBody);
        response.then().statusCode(204);
    }
}
