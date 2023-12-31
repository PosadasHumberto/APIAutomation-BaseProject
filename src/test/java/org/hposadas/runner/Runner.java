package org.hposadas.runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = "org/hposadas/steps",
        tags = "@ContainsInName or @ContainsInUsername or @ContainsIsNameOrUsername"
        /*,
        plugin = {
                "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:",
                "json:target/cucumber-reports.json"
        }*/
)
public class Runner {

}
