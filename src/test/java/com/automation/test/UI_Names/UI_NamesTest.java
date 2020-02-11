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


    String info = response.getBody().asString();

    System.out.println(info);
//    for (String Infos : info) {
//        System.out.println(Infos);
//
//    }

    //assertTrue(response.body(),);

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
    System.out.println(info);

}



}
