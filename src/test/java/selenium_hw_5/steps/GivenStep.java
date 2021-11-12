package selenium_hw_5.steps;

import selenium_hw_5.context.TestContext;
import io.cucumber.java.en.Given;
import org.apache.commons.configuration.Configuration;

public class GivenStep extends AbstractStep {

    @Given("I open JDI GitHub site")
    public void openHomePage() {
        homePage.open();
    }

    @Given("I login as user {string}")
    public void performLogin(String name) {
        Configuration properties = TestContext.getInstance().getTestObject("properties");
        homePage.getHeader().signInWithoutClear(
                (String) properties.getList(name).get(0),
                (String) properties.getList(name).get(1)
        );
    }
}
