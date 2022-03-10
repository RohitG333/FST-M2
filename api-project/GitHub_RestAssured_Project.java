package project;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class GitHub_RestAssured_Project {
    RequestSpecification requestSpec;
    String sshKey;
    int id;

    @BeforeClass
    public void setUp(){
        requestSpec = new RequestSpecBuilder().setContentType(ContentType.JSON)
                .addHeader("Authorization","token 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")
                .setBaseUri("https://api.github.com").build();

    }

    @Test(priority = 1)
    public void addSSHKey(){
        String reqBody="{\"title\": \"TestAPIKey\", \"key\": \"ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABAQCPxT800AKlexvjsa+XtR5QzCBrE/3RACU6X5RyJtE8RE5g9DvJca7WZGzNOrDERwXvzrIVizK+pq8ovx4GFZYuBF127OueO2Lrvo13R/DmtIzoantq6M2YKR/MxCnD6gsLe8BOW11JTj5z4vfQJxEF1KOP3HwxaeXP22javGqbKQL0TEaMBZ0yJv7P1nwVt3QjnI1k7WpROHuaiaHJewHAoeWcZArgjU94Sr2+k1fKIElbFjBER9LqXHdpVfD8tIEwjbFj00n3Tonj1AIZTjI81f0Di1M9CdMSaiKi55G5v4ZGRiD36ABdprbHHuWuH/EJJ7xabnccMmxxR3YcJ5X1\"}";
        Response response=given().spec(requestSpec).body(reqBody).when().post("/user/keys");
        System.out.println(response.getBody().asPrettyString());
        id=response.then().extract().path("id");
        sshKey=response.then().extract().path("key");
        System.out.println(id);
        System.out.println(sshKey);
        response.then().statusCode(201);
    }
    @Test(priority = 2)
    public void getSSHKey(){
        Response response=given().spec(requestSpec).when().get("/user/keys");
        System.out.println(response.getBody().asPrettyString());
        Reporter.log(response.getBody().asPrettyString());
        response.then().statusCode(200);
    }
    @Test(priority = 3)
    public void deleteSSHKey(){
        Response response = given().spec(requestSpec).pathParam("keyId",id).when().delete("/user/keys/{keyId}");
        System.out.println(response.getBody().asPrettyString());
        Reporter.log(response.getBody().asPrettyString());
        response.then().statusCode(204);
    }
}
