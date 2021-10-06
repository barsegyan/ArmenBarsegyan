package selenium_hw_3.ex1;


import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.assertj.core.api.Condition;
import org.assertj.core.api.SoftAssertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import selenium_hw_3.elements.Benefit;
import selenium_hw_3.pages.FramePage;
import selenium_hw_3.pages.HomePage;

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
        HomePage homePage = new HomePage(driver);

        // 2. Assert Browser title
        softAssertions
                .assertThat(homePage.getTitle())
                .isEqualTo(properties.getString("title"));

        // 3. Perform login
        homePage.getHeader().signInWithoutClear(
                properties.getString("login"),
                properties.getString("password")
        );

        softAssertions
                .assertThat(homePage.getHeader().isNameDisplayed())
                .isTrue();

        // 4. Assert Username is logged
        softAssertions
                .assertThat(homePage.getHeader().getName())
                .isEqualTo(properties.getString("name"));
        // 5. Assert that there are 4 items on the header section are displayed and they have proper texts
        softAssertions
                .assertThat(homePage.getHeader().getNavigationListText())
                .hasSize(properties.getInt("toolbar.size"))
                .isEqualTo(properties.getList("toolbar"));

        // 6. Assert that there are 4 images on the Index Page and they are displayed
        softAssertions
                .assertThat(homePage.getBenefits())
                .hasSize(properties.getInt("images.size"))
                .allMatch(Benefit::isIconDisplayed);

        // 7. Assert that there are 4 texts on the Index Page under icons and they have proper text
        List<String> textUnderImages = Arrays.asList(
                properties.getString("images.text.1"),
                properties.getString("images.text.2"),
                properties.getString("images.text.3"),
                properties.getString("images.text.4")
        );

        softAssertions
                .assertThat(homePage.getBenefits())
                .hasSize(properties.getInt("text.size"))
                .allMatch(Benefit::isBenefitTextDisplayed)
                .map(Benefit::getBenefitText)
                .isEqualTo(textUnderImages);

        // 8. Assert that there is the iframe with "Frame Button" exist
        softAssertions
                .assertThat(homePage.isThereFrame())
                .isTrue();

        // 9. Switch to the iframe and check that there is "Frame Button" in the iframe
        FramePage framePage = homePage.switchToFrame();
        softAssertions
                .assertThat(framePage.isFrameButtonDisplayed())
                .isTrue();

        // 10. Switch to original window back
        homePage = (HomePage) framePage.switchToDefault();

        // 11. Assert that there are 5 items in the Left Section are displayed and they have proper text
        softAssertions
                .assertThat(homePage.getLeftSection().getNavigationList())
                .are(new Condition<>(WebElement::isDisplayed, "all displayed"))
                .hasSize(properties.getInt("text.left.size"));

        softAssertions
                .assertThat(homePage.getLeftSection().getNavigationNamesList())
                .isEqualTo(properties.getList("text.left"));

        softAssertions.assertAll();

        // 12. Close Browser
        homePage.close();
    }

}
