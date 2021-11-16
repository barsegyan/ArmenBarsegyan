package selenium_hw_3.elements;

import selenium_hw_3.pages.DifferentElementsPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ru.yandex.qatools.htmlelements.element.HtmlElement;

import java.util.List;
import java.util.stream.Collectors;

@FindBy(tagName = "header")
public class Header extends HtmlElement {

    private static final String SERVICE = "Service";
    private static final String DIFFERENT_ELEMENTS = "Different elements";

    public WebDriver driver;

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

    @FindBy(css = "ul.nav")
    private WebElement navigation;

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

    public Header clickService() {
        getByNameInHeader(SERVICE).click();
        return this;
    }

    public DifferentElementsPage clickDifferentElement() {
        getByNameInServiceDropDownInHeader(DIFFERENT_ELEMENTS).click();
        return new DifferentElementsPage(driver);
    }

    public WebElement getByNameInHeader(String nameInHeader) {
        return navigation
                .findElement(By.xpath(String.format("./li/a[contains(text(), \"%s\")]", nameInHeader)));
    }

    public WebElement getByNameInServiceDropDownInHeader(String nameInService) {
        return getByNameInHeader(SERVICE)
                .findElement(By.xpath(String.format("//a[contains(text(), \"%s\")]", nameInService)));
    }
}
