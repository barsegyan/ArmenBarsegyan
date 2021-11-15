package selenium_hw_5.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = {
                "src/test/resources/hw5.features/UserTableForm.feature",
                "src/test/resources/hw5.features/UserTableFormVisibility.feature"},
        glue = {"hw5.steps"},
        tags = "@regression"
)
public class RegressionUserTableTests extends AbstractTestNGCucumberTests {
}
