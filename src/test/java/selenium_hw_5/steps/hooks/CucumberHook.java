package selenium_hw_5.steps.hooks;

import selenium_hw_5.context.TestContext;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

public class   CucumberHook {

    private final String pathToProperties = "src/test/resources/data/PageData.properties";

    @Before
    public void downloadAndSetUpWebDriver() throws ConfigurationException {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        TestContext.getInstance()
                .putTestObject("driver", driver)
                .putTestObject("properties", new PropertiesConfiguration(pathToProperties));
    }

    @After
    public void tearDownWebDriver() {
        WebDriver driver = TestContext.getInstance().getTestObject("driver");
        if (driver != null) {
            driver.quit();
        }
        TestContext.getInstance().clean();
    }
}
