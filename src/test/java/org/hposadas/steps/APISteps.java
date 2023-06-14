package org.hposadas.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import static io.restassured.RestAssured.*;

public class APISteps {

    private static RequestSpecification request;
    private static Response response;
    private ValidatableResponse json;

    @Given("^I send a GET request to the endpoint$")
    public void sendGetRequest(){
        request = given()
                .baseUri("https://api.github.com")          //URI
                .contentType(ContentType.JSON);
    }

    @Then("^I get a (\\d+) status code$")
    public void expectedStatusCode(int expectedStatusCode){
        response = request
                .when()
                .get("/users/PosadasHumberto/repos");       //endpoint

        json = response
                .then()
                .statusCode(expectedStatusCode);
    }

}
