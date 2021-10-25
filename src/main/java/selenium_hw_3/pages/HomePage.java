package selenium_hw_3.pages;

import selenium_hw_3.elements.Benefit;
import selenium_hw_3.elements.Header;
import selenium_hw_3.elements.LeftSection;
import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementDecorator;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementLocatorFactory;

import java.util.List;
import java.util.stream.Collectors;

public class HomePage extends BasePage {

    private static final String URL = "https://jdi-testing.github.io/jdi-light/index.html";

    @Getter private Header header;
    @Getter private LeftSection leftSection;

    @FindBy(className = "benefit")
    private List<WebElement> benefits;

    @FindBy(id = "frame")
    private WebElement frame;

    public HomePage(WebDriver driver) {
        PageFactory.initElements(new HtmlElementDecorator(new HtmlElementLocatorFactory(driver)),this);
        this.driver = driver;
        header.driver = driver;
        driver.get(URL);
    }

    public List<Benefit> getBenefits() {
        return benefits
                .stream()
                .map(Benefit::new)
                .collect(Collectors.toList());
    }

    public boolean isThereFrame() {
        return frame.isDisplayed();
    }

    public FramePage switchToFrame() {
        driver.switchTo().frame(frame);
        return new FramePage(this);
    }
}
