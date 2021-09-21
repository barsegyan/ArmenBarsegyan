package selenium_hw_2.ex1;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.*;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

public class Exercise1 {
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

        WebElement name = driver.findElement(By.id("user-name"));
        String s = name.getAttribute("innerHTML");
        Assert.assertEquals(s.toUpperCase(), "ROMAN IOVLEV");
        Assert.assertTrue(name.isDisplayed());
        Assert.assertEquals(driver.getTitle(), "Home Page");
    }

    @Test(testName = "Header",
            dependsOnMethods = {"login"})
    public void headerTest() {
        List<WebElement> list = driver.findElements(By.xpath("//header/div/nav/ul[1]/li/a"));
        Assert.assertEquals(list.size(), 4);
        final String[] names = {"HOME", "CONTACT FORM", "SERVICE", "METALS &AMP; COLORS"};
        for (int i = 0; i < 4; i++) {
            String s = list.get(i).getAttribute("innerHTML").split("<")[0].trim().toUpperCase();
            Assert.assertTrue(list.get(i).isDisplayed());
            Assert.assertEquals(s, names[i]);
        }
    }

    @Test(testName = "Icons",
            dependsOnMethods = {"login"})
    public void iconsTest() {
        //List<WebElement> list1 = driver.findElements(By.xpath("//*[@class='row clerafix benefits']/div"));
        List<WebElement> list1 = driver.findElements(By.className("benefit-icon"));
        List<WebElement> list2 = driver.findElements(By.className("benefit-txt"));
        Assert.assertEquals(list1.size(), 4);
        Assert.assertEquals(list2.size(), 4);
        String[] texts = {"To include good practices\nand ideas from successful\nEPAM project",
                "To be flexible and\ncustomizable",
                "To be multiplatform",
                "Already have good base\n(about 20 internal and\nsome external projects),\nwish to get more…"
        };
        for(WebElement e : list1) {
            //System.out.println(e.getAttribute("innerHTML"));
            Assert.assertTrue(e.isDisplayed());
        }
        for (int i = 0; i < 4; i++) {
            Assert.assertTrue(list2.get(i).isDisplayed());
            String s = list2.get(i).getAttribute("innerHTML").replaceAll("\\s*<br>\\s*", "\n").trim();
            Assert.assertEquals(s, texts[i]);
        }
    }

    @Test(testName = "Text",
            dependsOnMethods = {"login"})
    public void textTest() {
        List<WebElement> headers = driver.findElements(By.tagName("h3"));
        WebElement title = headers.get(0);
        WebElement subHeader = headers.get(1).findElement(By.tagName("a"));
        WebElement text = driver.findElement(By.name("jdi-text"));
        Assert.assertTrue(title.isDisplayed());
        Assert.assertTrue(text.isDisplayed());
        Assert.assertEquals(title.getAttribute("innerHTML").trim(), "EPAM framework Wishes…");
        String displayedText = text.getAttribute("innerHTML").replaceAll("\\s+|\n", " ").trim();
        Assert.assertEquals(displayedText,
                "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.");
        Assert.assertTrue(headers.get(1).isDisplayed());
        Assert.assertEquals(subHeader.getAttribute("innerHTML").toUpperCase().trim(), "JDI GITHUB");
        Assert.assertEquals(subHeader.getAttribute("href"), "https://github.com/epam/JDI");
    }

    @Test(testName = "IFrame",
            dependsOnMethods = {"login"})
    public void iFrameTest() {
        WebElement iframe = driver.findElement(By.id("second_frame"));
        Assert.assertTrue(iframe.isDisplayed());
        driver.switchTo().frame(iframe);
        Assert.assertTrue(driver.findElement(By.id("epam-logo")).isDisplayed());
        driver.switchTo().parentFrame();
    }

    @Test(testName = "Sections",
            dependsOnMethods = {"login"})
    public void sectionsTest() {
        Assert.assertTrue(driver.findElement(By.id("mCSB_1")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.tagName("footer")).isDisplayed());
    }

    @AfterClass
    public void closeDriver() {
        driver.quit();
    }
}
