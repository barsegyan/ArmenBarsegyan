package selenium_hw_5.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = {"src/test/resources/hw5.features/DifferentElementsForm.feature"},
        glue = {"hw5.steps"},
        tags = "@smoke"
)
public class SmokeDifferentElementsFormTest extends AbstractTestNGCucumberTests {
}
