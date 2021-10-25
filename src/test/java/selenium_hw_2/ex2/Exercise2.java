package selenium_hw_2.ex2;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import org.testng.annotations.*;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

public class Exercise2 {
    private WebDriver driver;

    @BeforeClass
    public void initDriver() {
        System.setProperty("webdriver.chrome.driver","src/main/resources/chromedriver");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test(testName = "Open site")
    public void openPage() {
        String mainURL = "https://jdi-testing.github.io/jdi-light/index.html";
        driver.navigate().to(mainURL);
        Assert.assertEquals(driver.getTitle(), "Home Page");
    }

    @Test(testName = "Login",
            dependsOnMethods = {"openPage"})
    public void login() {
        driver.findElement(By.id("user-icon")).click();
        driver.findElement(By.id("name")).sendKeys("Roman");
        driver.findElement(By.id("password")).sendKeys("Jdi1234");
        driver.findElement(By.id("login-button")).click();

        SoftAssert softAssert = new SoftAssert();

        WebElement name = driver.findElement(By.id("user-name"));
        String s = name.getAttribute("innerHTML");
        softAssert.assertEquals(s.toUpperCase(), "ROMAN IOVLEV");
        softAssert.assertTrue(name.isDisplayed());
        softAssert.assertEquals(driver.getTitle(), "Home Page");
        softAssert.assertAll();
    }

    @Test(testName = "DropDown",
            dependsOnMethods = {"login"},
            groups = {"firstPage"})
    public void dropDownTest() {
        SoftAssert softAssert = new SoftAssert();
        WebElement dropDown = driver.findElement(By.className("dropdown"));
        dropDown.click();
        List<WebElement> menu = dropDown.findElements(By.tagName("li"));
        final String[] options = {
                "Support",
                "Dates",
                "Search",
                "Complex Table",
                "Simple Table",
                "User Table",
                "Table with pages",
                "Different elements",
                "Performance"
        };
        Assert.assertEquals(menu.size(), options.length);
        for (int i = 0; i < options.length; i++) {
            WebElement href = menu.get(i).findElement(By.tagName("a"));
            softAssert.assertTrue(href.isDisplayed());
            softAssert.assertEquals(href.getAttribute("innerHTML").toUpperCase().trim(),
                    options[i].toUpperCase());
        }
        softAssert.assertAll();
    }

    @Test(dependsOnMethods = {"login"},
            groups = {"firstPage"})
    public void sideMenuTest() {
        final String[] options = {
                "Support",
                "Dates",
                "Complex Table",
                "Simple Table",
                "Search",
                "User Table",
                "Table with pages",
                "Different elements",
                "Performance"
        };
        SoftAssert softAssert = new SoftAssert();
        WebElement service = driver.findElement(By.xpath("//*[@class='sidebar-menu']/li[3]"));
        service.click();
        List<WebElement> menu = service.findElements(By.tagName("li"));
        Assert.assertEquals(menu.size(), options.length);
        for (int i = 0; i < options.length; i++) {
            WebElement item = menu.get(i).findElement(By.tagName("span"));
            softAssert.assertTrue(item.isDisplayed());
            softAssert.assertEquals(item.getAttribute("innerHTML").trim(), options[i]);
        }
        softAssert.assertAll();
    }

    @Test(dependsOnGroups = {"firstPage"})
    public void elementsPage() {
        WebElement dropDown = driver.findElement(By.className("dropdown"));
        dropDown.click();
        dropDown.findElement(By.xpath("//li[8]")).click();
    }

    @Test(dependsOnMethods = {"elementsPage"},
            groups = {"secondPage"})
    public void elementsExist() {
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(driver.findElement(By.name("navigation-sidebar")).isDisplayed());
        softAssert.assertTrue(driver.findElement(By.name("log-sidebar")).isDisplayed());
        String[] checkBoxes = {"Water", "Earth", "Wind", "Fire"};
        String[] radioButtons = {"Gold", "Silver", "Bronze", "Selen"};
        String[] optionsLabels = {"Red", "Green", "Blue", "Yellow"};
        List<WebElement> weCB = driver.findElements(By.className("label-checkbox"));
        List<WebElement> weRB = driver.findElements(By.className("label-radio"));
        softAssert.assertEquals(weCB.size(), checkBoxes.length);
        softAssert.assertEquals(weRB.size(), radioButtons.length);
        softAssert.assertTrue(driver.findElement(By.tagName("select")).isDisplayed());
        List<WebElement> weOp = driver.findElements(By.tagName("option"));
        softAssert.assertEquals(weOp.size(), optionsLabels.length);
        softAssert.assertAll();
        for (int i = 0; i < 4; i++) {
            WebElement cb = weCB.get(i);//.findElement(By.tagName("input"));
            WebElement rb = weRB.get(i);//.findElement(By.tagName("input"));
            softAssert.assertEquals(cb.getText().trim(), checkBoxes[i]);
            softAssert.assertEquals(rb.getText().trim(), radioButtons[i]);
            softAssert.assertEquals(weOp.get(i).getAttribute("innerHTML").trim(), optionsLabels[i]);
        }
        softAssert.assertEquals(driver.findElement(By.xpath("//main//button")).getAttribute("innerHTML").trim(),
                "Default Button");
        softAssert.assertEquals(driver.findElement(By.xpath("//main//input[@class='uui-button']")).getAttribute("value").trim(),
                "Button");
        softAssert.assertAll();
    }

    @Test(dependsOnMethods = {"elementsPage"},
            groups = {"secondPage"})
    public void clickElements() {
        List<WebElement> checkboxes = driver.findElements(By.xpath("//main//input[@type='checkbox']"));
        List<WebElement> radios = driver.findElements(By.xpath("//main//input[@type='radio']"));
        WebElement selen = radios.get(radios.size() - 1);
        WebElement logs = driver.findElement(By.className("panel-body-list"));
        SoftAssert softAssert = new SoftAssert();
        checkboxes.get(0).click();
        checkboxes.get(2).click();
        softAssert.assertTrue(checkboxes.get(0).isSelected());
        softAssert.assertTrue(checkboxes.get(2).isSelected());
        String[] logsString = logs.getText().split("\n");
        Assert.assertEquals(logsString.length, 2);
        assertLogs(logsString[0], "Wind", "true", softAssert);
        assertLogs(logsString[1], "Water", "true", softAssert);
        softAssert.assertAll();
        selen.click();
        softAssert.assertTrue(selen.isSelected());
        logsString = logs.getText().split("\n");
        Assert.assertEquals(logsString.length, 3);
        assertLogs(logsString[0], "metal", "Selen", softAssert);
        softAssert.assertAll();
        WebElement select = driver.findElement(By.xpath("//main//select"));
        select.click();
        select.findElement(By.xpath("*[last()]")).click();
        logsString = logs.getText().split("\n");
        Assert.assertEquals(logsString.length, 4);
        assertLogs(logsString[0], "Colors", "Yellow", softAssert);
        softAssert.assertAll();
        checkboxes.get(0).click();
        checkboxes.get(2).click();
        softAssert.assertFalse(checkboxes.get(0).isSelected());
        softAssert.assertFalse(checkboxes.get(2).isSelected());
        logsString = logs.getText().split("\n");
        Assert.assertEquals(logsString.length, 6);
        assertLogs(logsString[0], "Wind", "false", softAssert);
        assertLogs(logsString[1], "Water", "false", softAssert);
        softAssert.assertAll();
    }

    void assertLogs(String log, String key, String value, SoftAssert softAssert) {
        String[] parsed = log.split("\\s");
        softAssert.assertEquals(parsed[1].substring(0, parsed[1].length() - 1), key);
        softAssert.assertEquals(parsed[parsed.length - 1], value);
    }


    @AfterClass
    public void closeDriver() {
        driver.quit();
    }
}
