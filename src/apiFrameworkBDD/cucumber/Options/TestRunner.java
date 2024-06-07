package apiFrameworkBDD.cucumber.Options;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class) 
@CucumberOptions(features = "src/ApiFrameworkBdd/feature",plugin="json:target/jsonReports/cucumber-report.json",glue= {"apiFrameworkBDD/stepDefinations"},tags = "@AddPlace")
public class TestRunner {
	
}

//mvn test -DapiFrameworkBDD.cucumber.Options="--tags @AddPlace"
//https://github.com/damianszczepanik/maven-cucumber-reporting/blob/master/README.md