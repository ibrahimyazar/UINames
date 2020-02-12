package com.automation.test.UI_Names;

//import com.automation.utilities.ConfigurationReader;
import static io.restassured.RestAssured.*;

import com.automation.test.utilities.ConfigurationReader;
import com.github.javafaker.Faker;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.codehaus.groovy.transform.SourceURIASTTransformation;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UI_NamesTest {

    @BeforeAll
    public static void setup(){
        baseURI = ConfigurationReader.getProperty("UINames_URI");
    }

/*
No params test
1. Send a get request without providing any parameters
2. Verify status code 200, content type application/json;charset=utf-8
3. Verify that name, surname, gender, region fields have value
 */
@Test
    public void no_Param_Test(){
    Response response = given()
                            .accept(ContentType.JSON)
                        .get().prettyPeek();
    assertEquals(200, response.statusCode());
    assertEquals("application/json; charset=utf-8", response.contentType());
    response.then().assertThat().body("name", not(empty()));
    response.then().assertThat().body("surname", not(empty()));
    response.then().assertThat().body("region", not(empty()));
    response.then().assertThat().body("gender", not(empty()));

}

/*
Gender test
1. Create a request by providing query parameter: gender, male or female
2. Verify status code 200, content type application/json; charset=utf-8
3. Verify that value of gender field is same from step 1
 */
@DisplayName("Verify that value of gender field is same from step 1")
@Test
    public void Gender_Test(){

    String gender = "male";
    Response response = given().accept(ContentType.JSON)
                            .queryParam("gender", gender)
                        .get("?gender=male").prettyPeek();
    assertEquals(200, response.statusCode());
    assertEquals("application/json; charset=utf-8", response.contentType());
    response.then().assertThat().body("gender", is(gender));
    String info = response.getBody().asString();
    String genders = response.jsonPath().getString("gender");
    System.out.println(genders);
    System.out.println(info);

}

/*
2 params test
1. Create a request by providing query parameters: a valid region and gender NOTE: Available region values are given in the documentation
2. Verify status code 200, content type application/json; charset=utf-8
3. Verify that value of gender field is same from step 1
4. Verify that value of region field is same from step 1
 */

    @Test
    @DisplayName("Verify that value of region field is same from step 1")
    public void Two_Params_Test(){

        String region = "Greece";
        String gender = "female";

        Response response = given()
                                .accept(ContentType.JSON)
                                .queryParam("region", region)
                                .queryParam("gender", gender)
                            .when()
                                .get().prettyPeek();
        response.then().assertThat().statusCode(200).
                                    contentType("application/json; charset=utf-8").
                                    body("gender", is(gender))
                                    .body("region", is(region));
    }

    /*
    Invalid gender test
1. Create a request by providing query parameter: invalid gender
2. Verify status code 400 and status line contains Bad Request
3. Verify that value of error field is "Invalid gender"
     */
    @Test
    @DisplayName("Verify that value of error field is Invalid gender")
    public void invalid_Gender_Test(){

        Response response = given()
                                .accept(ContentType.JSON)
                                .queryParam("gender", "invalid")
                            .get();
        assertEquals(400, response.statusCode());
        assertTrue( response.statusLine().contains("Bad Request"));
        assertEquals("Invalid gender", response.jsonPath().getString("error"));
        System.out.println(response.jsonPath().getString("error"));
    }


}
