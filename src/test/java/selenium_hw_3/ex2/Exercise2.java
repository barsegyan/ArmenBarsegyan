package selenium_hw_3.ex2;


import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.yandex.qatools.htmlelements.element.CheckBox;
import ru.yandex.qatools.htmlelements.element.Radio;
import selenium_hw_3.pages.DifferentElementsPage;
import selenium_hw_3.pages.HomePage;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

public class Exercise2 {
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
    public void Ex2Test() {
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

        softAssertions.assertAll();

        // 5. Open through the header menu Service -> Different Elements Page
        DifferentElementsPage differentElementsPage = homePage.getHeader()
                .clickService()
                .clickDifferentElement();

        assertThat(differentElementsPage.getURL())
                .isEqualTo(properties.getString("diffURL"));

        // 6. Select checkboxes
        CheckBox water = differentElementsPage.getMain().getCheckBox("Water");
        CheckBox wind = differentElementsPage.getMain().getCheckBox("Wind");
        water.click();
        wind.click();
        assertThat(water.isSelected()).isTrue();
        assertThat(wind.isSelected()).isTrue();

        // 7. Select radio
        Radio selen = differentElementsPage.getMain().getRadio("Selen");
        selen.click();
        assertThat(selen.isSelected()).isTrue();

        // 8. Select in dropdown
        Select dropdown = differentElementsPage.getMain().getDropDown();
        String color = "Yellow";
        dropdown.selectByVisibleText(color);
        Assertions.assertThat(dropdown.getAllSelectedOptions())
                .hasSize(1)
                .map(WebElement::getText)
                .first()
                .isEqualTo(color);

        // 9. Assert that for each checkbox there is an individual log row and value is
        // corresponded to the status of checkbox
        differentElementsPage.getMain().clearCheckBoxes();

        for (Object checkBoxName : properties.getList("checkbox")) {
            for (Boolean expectedState : new Boolean[] {true, false}) {
                differentElementsPage.getMain().getCheckBox((String) checkBoxName).click();
                assertThat(differentElementsPage.getRightSection().getLastLog())
                        .contains(checkBoxName + properties.getString("checkbox.log") + " " + expectedState);
            }

        }

        // 9. Assert that for radio button there is a log row and value is
        // corresponded to the status of radio button
        for (Object radioName : properties.getList("radio")) {
            differentElementsPage.getMain().getRadio((String) radioName).click();
            assertThat(differentElementsPage.getRightSection().getLastLog())
                    .contains(properties.getString("radio.log") + " " + radioName);
        }

        // 9. Assert that for dropdown there is a log row and value is
        // corresponded to the selected value
        dropdown = differentElementsPage.getMain().getDropDown();
        for (WebElement option : dropdown.getOptions()) {
            dropdown.selectByVisibleText(option.getText());
            assertThat(differentElementsPage.getRightSection().getLastLog())
                    .contains(properties.getString("color.log") + " " + option.getText());
        }

        // 10. Close Browser
        differentElementsPage.close();
    }

}




