package selenium_hw_3.pages;

import selenium_hw_3.elements.LeftSection;
import selenium_hw_3.elements.RightSection;
import selenium_hw_3.elements.UserTable;
import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementDecorator;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementLocatorFactory;

public class UserTablePage extends BasePage {

    @Getter private UserTable userTable;
    @Getter private LeftSection leftSection;
    @Getter private RightSection rightSection;

    public UserTablePage(WebDriver driver) {
        PageFactory.initElements(new HtmlElementDecorator(new HtmlElementLocatorFactory(driver)),this);
        this.driver = driver;
    }
}
