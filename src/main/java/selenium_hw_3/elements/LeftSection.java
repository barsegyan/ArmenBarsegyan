package selenium_hw_3.elements;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ru.yandex.qatools.htmlelements.element.HtmlElement;

import java.util.List;
import java.util.stream.Collectors;

@FindBy(css = "ul.sidebar-menu")
public class LeftSection extends HtmlElement {

    @FindBy(css = "ul.sidebar-menu>li")
    private List<WebElement> navigationList;

    public List<WebElement> getNavigationList() {
        return navigationList;
    }

    public List<String> getNavigationNamesList() {
        return navigationList
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }
}