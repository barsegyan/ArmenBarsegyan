package selenium_hw_2.ex2;


import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

public class Excercise2 {
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

        softAssertions.assertAll();

        // 5. Open through the header menu Service -> Different Elements Page
        WebElement service = driver.findElement(By.xpath("//li/a[contains(text(),\"Service\")]/.."));
        service.click();
        service.findElement(By.xpath("//a[contains(text(),\"Different elements\")]")).click();

        assertThat(driver.getCurrentUrl())
                .isEqualTo(properties.getString("diffURL"));

        // 6. Select checkboxes
        WebElement water = DiffPageInput.getCheckBoxByName(driver,"Water");
        WebElement wind = DiffPageInput.getCheckBoxByName(driver,"Wind");
        water.click();
        wind.click();
        assertThat(water.isSelected()).isTrue();
        assertThat(wind.isSelected()).isTrue();

        // 7. Select radio
        WebElement selen = DiffPageInput.getRadioButtonByName(driver, "Selen");
        selen.click();
        assertThat(selen.isSelected()).isTrue();

        // 8. Select in dropdown
        Select dropdown = new Select(driver.findElement(By.tagName("select")));
        String color = "Yellow";
        dropdown.selectByVisibleText(color);
        Assertions.assertThat(dropdown.getAllSelectedOptions())
                .hasSize(1)
                .map(WebElement::getText)
                .first()
                .isEqualTo(color);

        // 9. Assert that for each checkbox there is an individual log row and value is
        // corresponded to the status of checkbox
        water.click();
        wind.click();
        DiffPageInput.checkAllCheckBoxLogs(driver, true, properties);
        DiffPageInput.checkAllCheckBoxLogs(driver, false, properties);

        // 9. Assert that for radio button there is a log row and value is
        // corresponded to the status of radio button
        for (Object radioButtonNameObj : properties.getList("radio")) {
            String checkBoxName = (String) radioButtonNameObj;
            DiffPageInput.getRadioButtonByName(driver, checkBoxName).click();
            List<WebElement> logs = driver.findElements(By.cssSelector(".panel-body-list>li"));
            String actualLog = logs.get(0).getText();
            assertThat(actualLog)
                    .contains(properties.getString("radio.log") + " " + checkBoxName);
        }

        // 9. Assert that for dropdown there is a log row and value is
        // corresponded to the selected value
        dropdown = new Select(driver.findElement(By.tagName("select")));
        for (WebElement option : dropdown.getOptions()) {
            dropdown.selectByVisibleText(option.getText());
            List<WebElement> logs = driver.findElements(By.cssSelector(".panel-body-list>li"));
            String actualLog = logs.get(0).getText();
            assertThat(actualLog)
                    .contains(properties.getString("color.log") + " " + option.getText());
        }

        // 10. Close Browser
        driver.close();
    }

}




class DiffPageInput {

    public static WebElement getCheckBoxByName(WebDriver driver, String name) {
        Optional<WebElement> checkBox = driver
                .findElements(By.className("label-checkbox"))
                .stream()
                .filter(elem -> elem.getText().contains(name))
                .findFirst();
        if (!checkBox.isPresent()) {
            Assert.fail("Can't find checkBox by name: " + name);
        }
        return checkBox.get().findElement(By.tagName("input"));

    }

    public static WebElement getRadioButtonByName(WebDriver driver, String name) {
        Optional<WebElement> radioButton = driver
                .findElements(By.className("label-radio"))
                .stream()
                .filter(elem -> elem.getText().contains(name))
                .findFirst();
        if (!radioButton.isPresent()) {
            Assert.fail("Can't find radioButton by name: " + name);
        }
        return radioButton.get().findElement(By.tagName("input"));
    }

    public static void checkAllCheckBoxLogs(WebDriver driver, boolean expectedState, Configuration properties) {
        for (Object checkBoxNameObj : properties.getList("checkbox")) {
            String checkBoxName = (String) checkBoxNameObj;
            getCheckBoxByName(driver, checkBoxName).click();
            List<WebElement> logs = driver.findElements(By.cssSelector(".panel-body-list>li"));
            String actualLog = logs.get(0).getText();
            assertThat(actualLog)
                    .contains(checkBoxName + properties.getString("checkbox.log") + " " + expectedState);
        }
    }

}
