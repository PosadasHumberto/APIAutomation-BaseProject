package org.hposadas.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class APISteps {

    @Given("^I send a GET request to the endpoint$")
    public void sendGetRequest(){

    }

    @Then("^I get a list of (\\d+) users$")
    public void validateListUsersSize(int expectedUserSize){

    }

}
