package selenium_hw_3.pages;

import selenium_hw_3.elements.Header;
import selenium_hw_3.elements.LeftSection;
import selenium_hw_3.elements.Main;
import selenium_hw_3.elements.RightSection;
import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementDecorator;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementLocatorFactory;

public class DifferentElementsPage extends BasePage {

    @Getter private Header header;
    @Getter private Main main;
    @Getter private LeftSection leftSection;
    @Getter private RightSection rightSection;

    public DifferentElementsPage(WebDriver driver) {
        PageFactory.initElements(new HtmlElementDecorator(new HtmlElementLocatorFactory(driver)),this);
        this.driver = driver;
        header.driver = driver;
    }
}
