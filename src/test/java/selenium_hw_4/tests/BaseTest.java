package selenium_hw_4.tests;

import selenium_hw_4.steps.ActionStep;
import selenium_hw_4.steps.AssertionStep;
import selenium_hw_4.steps.ComplexStep;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import java.util.concurrent.TimeUnit;

public abstract class BaseTest {

    protected ActionStep actionStep;
    protected AssertionStep assertionStep;
    protected ComplexStep complexStep;

    protected WebDriver driver;
    protected Configuration properties;
    private final String pathToProperties = "src/test/resources/data/PageData.properties";

    @BeforeClass(description = "Download WebDriver By WebManager")
    public static void downloadWebDriver() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeMethod(description = "Set up WebDriver and Read Configuration")
    public void setUpDriverAndProperties(ITestContext context) throws ConfigurationException {
        properties =  new PropertiesConfiguration(pathToProperties);
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        context.setAttribute("driver", driver);
        actionStep = new ActionStep(driver);
        assertionStep = new AssertionStep(driver);
        complexStep = new ComplexStep(driver, actionStep, assertionStep);
    }

    @AfterMethod(description = "Tear down WebDriver")
    public void tearDownDriver() {
        if (driver != null) {
            driver.quit();
        }
    }

}
