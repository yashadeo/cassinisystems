package com.cassini.test;

import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;


@CucumberOptions(features = "src/test/resources/features", tags = {"@api"}, monochrome = true, plugin = {
        "pretty", "html:target/cucumber-report/",
        "json:target/cucumber-report/runapiat/cucumber.json",
        "rerun:target/cucumber-report/runapiat/rerun.txt"},
        glue = "com.cassini.test")
public class RunApiATSuite extends AbstractTestNGCucumberTests {
}
