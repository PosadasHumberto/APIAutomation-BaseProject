package org.hposadas.steps;

import io.cucumber.java.en.*;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.Matchers;

import java.io.File;

import static io.restassured.RestAssured.given;

public class BraveNewCoinAPISteps {

    private static RequestSpecification requestSpecification;
    private Response response;
    private ValidatableResponse json;
    private String accessToken;

    @Given("^I have a valid API Key for the (.+) URI$")
    public void iSetRequestParams(String uri) {
        requestSpecification = given()
                .contentType(ContentType.JSON)
                .header("X-RapidAPI-Key","e1cd1a34d6mshdf21237e2507354p1603b7jsn916eead37d17")
                .header("X-RapidAPI-Host","bravenewcoin.p.rapidapi.com")
                .baseUri(uri)
                .log().all();   //Permite hacer logs de toda la parte del request en consola.
    }

    @When("^I send a POST request with a valid (.+) payload to the (.+) endpoint$")
    public void SendValidPOSTRequest(String payload, String endpoint){

        File requestBody = new File("src/test/resources/payload/" + payload + ".json");

        response = requestSpecification
                .when()
                .body(requestBody)
                .post(endpoint)
                .prettyPeek();      //imprime la respuesta en consola
    }

    @Then("^I can validate I receive a valid token in the response$")
    public void validateTheToken(){
        json = response
                .then()
                .statusCode(200) // Verifica que el código de estado sea 200 (éxito)
                .body("access_token", Matchers.not(Matchers.is(Matchers.emptyOrNullString())));  //Verifica que el atributo "access_token" este presente y no sea nulo
        accessToken = response
                .jsonPath().getString("access_token");
    }

    @Given("^I have an invalid API Key for the (.+) URI$")
    public void iSetWrongRequestsParams(String uri) {
        requestSpecification = given()
                .contentType(ContentType.JSON)
                .header("X-RapidAPI-Key","Wrong API Key")
                .header("X-RapidAPI-Host","bravenewcoin.p.rapidapi.com")
                .baseUri(uri)
                .log().all();   //Permite hacer logs de toda la parte del request en consola.
    }

    @Then("^I receive an HTTP Code Status (\\d+)$")
    public void validatingstatusExpected(int expectedstatus) {
        json = response
                .then()
                .statusCode(expectedstatus);
    }

    @When("^I send a POST request without grant_type in it's body to the (.+) endpoint$")
    public void SendInvalidPOSTRequest(String endpoint) {

        String wrongBody = "{\n" +
                "    \"audience\": \"https://api.bravenewcoin.com\",\n" +
                "    \"client_id\": \"oCdQoZoI96ERE9HY3sQ7JmbACfBf55RY\",\n" +
                "}";

        response = requestSpecification
                .when()
                .body(wrongBody)
                .post(endpoint)
                .prettyPeek();
    }


}
