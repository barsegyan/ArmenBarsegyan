package selenium_hw_5.steps;

import selenium_hw_3.pages.DifferentElementsPage;
import selenium_hw_3.pages.HomePage;
import selenium_hw_3.pages.UserTablePage;
import selenium_hw_5.context.TestContext;
import org.openqa.selenium.WebDriver;

public abstract class AbstractStep {

    protected WebDriver driver;

    protected HomePage homePage;
    protected DifferentElementsPage differentElementsPage;
    protected UserTablePage userTablePage;

    public AbstractStep() {
        driver = TestContext.getInstance().getTestObject("driver");
        homePage = new HomePage(driver);
        differentElementsPage = new DifferentElementsPage(driver);
        userTablePage = new UserTablePage(driver);
    }
}
