package selenium_hw_3.elements;

import selenium_hw_3.pages.DifferentElementsPage;
import selenium_hw_3.pages.HomePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ru.yandex.qatools.htmlelements.element.HtmlElement;

import java.util.List;
import java.util.stream.Collectors;

@FindBy(tagName = "header")
public class Header extends HtmlElement {

    public WebDriver driver;

    private static final By differentElementLocator = By.xpath("//a[contains(text(), \"Different elements\")]");

    @FindBy(id = "name")
    private WebElement loginField;

    @FindBy(id = "password")
    private WebElement passwdField;

    @FindBy(css = "ul.navbar-right")
    private WebElement loginPasswdMenu;

    @FindBy(id = "login-button")
    private WebElement loginBtn;

    @FindBy(id = "user-name")
    private WebElement name;

    @FindBy(css = "ul.nav>li")
    private List<WebElement> navigationList;

    public Header inputLogin(String login) {
        loginField.sendKeys(login);
        return this;
    }

    public Header inputPasswd(String passwd) {
        passwdField.sendKeys(passwd);
        return this;
    }

    public Header clickLoginBtn() {
        loginBtn.click();
        return this;
    }

    public Header openMenu() {
        loginPasswdMenu.click();
        return this;
    }

    public String getName() {
        return name.getAttribute("innerText");
    }

    public boolean isNameDisplayed() {
        return name.isDisplayed();
    }

    public Header signInWithoutClear(String login, String passwd) {
        loginPasswdMenu.click();
        loginField.sendKeys(login);
        passwdField.sendKeys(passwd);
        loginBtn.click();
        return this;
    }

    public List<String> getNavigationListText() {
        return navigationList
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    public HomePage clickHome() {
        navigationList.get(0).click();
        return new HomePage(driver);
    }

    public Header clickService() {
        navigationList.get(2).click();
        return this;
    }

    public DifferentElementsPage clickDifferentElement() {
        driver.findElement(differentElementLocator).click();
        return new DifferentElementsPage(driver);
    }
}
