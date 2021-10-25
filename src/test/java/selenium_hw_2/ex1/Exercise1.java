package selenium_hw_2.ex1;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.assertj.core.api.Condition;
import org.assertj.core.api.SoftAssertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Exercise1 {

    protected WebDriver driver;
    protected Configuration properties;
    private final String pathToProperties = "src/test/resources/PageData.properties";
    SoftAssertions softAssertions = new SoftAssertions();
    @BeforeClass

    public void setUpDriverAndProperties() throws ConfigurationException {

        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        properties = new PropertiesConfiguration(pathToProperties);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

    }
        @AfterClass
        public void tearDownDriver() {
            if (driver != null) {
                driver.quit();
            }
        }






    @Test
    public void Ex1Test() {
        SoftAssertions softAssertions = new SoftAssertions();

        // 1. Open test site by URL
        driver.get(properties.getString("url"));

        // 2. Assert Browser title
        softAssertions
                .assertThat(driver.getTitle())
                .isEqualTo(properties.getString("title"));

        // 3. Perform login
        driver.findElement(By.cssSelector("ul.navbar-right")).click();
        driver.findElement(By.id("name")).sendKeys(properties.getString("login"));
        driver.findElement(By.id("password")).sendKeys(properties.getString("password"));
        driver.findElement(By.id("login-button")).click();

        softAssertions
                .assertThat(driver.findElement(By.id("user-name")).isDisplayed())
                .isTrue();

        // 4. Assert Username is loggined
        softAssertions
                .assertThat(driver.findElement(By.id("user-name")).getText())
                .isEqualTo(properties.getString("name"));

        // 5. Assert that there are 4 items on the header section are displayed and they have proper texts
        softAssertions
                .assertThat(driver.findElements(By.cssSelector("ul.nav>li")))
                .map(WebElement::getText)
                .hasSize(properties.getInt("toolbar.size"))
                .isEqualTo(properties.getList("toolbar"));

        // 6. Assert that there are 4 images on the Index Page and they are displayed
        softAssertions
                .assertThat(driver.findElements(By.cssSelector("div.benefit-icon")))
                .are(new Condition<>(WebElement::isDisplayed, "all displayed"))
                .hasSize(properties.getInt("images.size"));

        // 7. Assert that there are 4 texts on the Index Page under icons and they have proper text
        List<String> textUnderImages = Arrays.asList(
                properties.getString("images.text.1"),
                properties.getString("images.text.2"),
                properties.getString("images.text.3"),
                properties.getString("images.text.4")
        );

        softAssertions
                .assertThat(driver.findElements(By.cssSelector("span.benefit-txt")))
                .hasSize(properties.getInt("text.size"))
                .are(new Condition<>(WebElement::isDisplayed, "all displayed"))
                .map(WebElement::getText)
                .isEqualTo(textUnderImages);

        // 8. Assert that there is the iframe with "Frame Button" exist
        softAssertions
                .assertThat(driver.findElement(By.id("frame")))
                .is(new Condition<>(WebElement::isDisplayed, "frame displayed"));

        // 9. Switch to the iframe and check that there is "Frame Button" in the iframe
        driver.switchTo().frame(driver.findElement(By.id("frame")));
        softAssertions
                .assertThat(driver.findElement(By.id("frame-button")))
                .is(new Condition<>(WebElement::isDisplayed, "button displayed"));


        driver.switchTo().defaultContent();

        // 11. Assert that there are 5 items in the Left Section are displayed and they have proper text
        softAssertions
                .assertThat(driver.findElements(By.cssSelector("ul.sidebar-menu>li")))
                .are(new Condition<>(WebElement::isDisplayed, "all displayed"))
                .hasSize(properties.getInt("text.left.size"))
                .map(WebElement::getText)
                .isEqualTo(properties.getList("text.left"));

        softAssertions.assertAll();

        // 12. Close Browser
        driver.close();
    }

}
