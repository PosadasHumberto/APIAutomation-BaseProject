package org.hposadas.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class APISteps {

    private static RequestSpecification request;
    private static Response response;
    private ValidatableResponse json;

    @Given("^I send a GET request to the (.+) URI$")
    public void sendGetRequest(String uri){
        request = given()
                .baseUri(uri)          //URI
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

    @Then("^I validate there are (\\d+) items on the (.+) endpoint$")
    public void validateSize(int expectedSize, String endpoint) {
        response = request
                .when()
                .get(endpoint);

        List<String> jsonResponse = response.jsonPath().getList("$");

        assertEquals(expectedSize, jsonResponse.size());
    }

    @Then("^I validate there is a name (.+) in the response at (.+) endpoint$")
    public void validateContainsByName(String valueToFind, String endpoint) {
        response = request
                .when()
                .get(endpoint);

        List<Map<String, String>> jsonResponse = response.jsonPath().getList("$");

        boolean isValueThere = jsonResponse
                .stream()
                        .anyMatch(map -> map.get("name").contains(valueToFind));

        assertTrue(
                "The name " + valueToFind + " is not in the list",
                isValueThere
        );
    }

    @Then("^I validate there is a username (.+) in the response at (.+) endpoint$")
    public void validateContainsByUsername(String valueToFind, String endpoint) {
        response = request
                .when()
                .get(endpoint);
        List<String> usernames = response.jsonPath().getList("username");

        assertTrue(
                "The username " + valueToFind + " is not in the list",
                usernames.contains(valueToFind)
                );

    }


    @Then("^I validate there is a value (.+) as a (.+) in the response at (.+) endpoint$")
    public void validateContainsByNameOrUsername(String value, String type, String endpoint) {
        response = request
                .when()
                .get(endpoint);

        boolean isValueThere = false;

        if (type.equals("username")){
            List<String> usernamesResponse = response.jsonPath().getList("username");
            isValueThere = usernamesResponse.contains(value);
        } else if (type.equals("name")) {
            List<String> namesResponse = response.jsonPath().getList("name");
            for (String name: namesResponse) {
                if (name.contains(value)){
                    isValueThere = true;
                    break;
                }
            }
        }

        assertTrue(
                "The value " + value + " of type " + type + " is not in the list",
                isValueThere
        );
    }


    @Then("^I can validate the nested value (.+) on the response at (.+) endpoint$")
    public void validateContainsNested(String expectedStreet, String endpoint) {
        response = request
                .when()
                .get(endpoint);

        String jsonResponse = response.jsonPath().getString("address.street");

        assertTrue(
                "The street " + expectedStreet + " is not assosiated to any user",
                jsonResponse.contains(expectedStreet)
                );
    }

}
