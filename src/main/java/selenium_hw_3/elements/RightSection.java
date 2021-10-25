package selenium_hw_3.elements;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ru.yandex.qatools.htmlelements.element.HtmlElement;

import java.util.List;
import java.util.stream.Collectors;

@FindBy(css = "ul.logs")
public class RightSection extends HtmlElement {

    @FindBy(tagName = "li")
    private List<WebElement> logs;

    public List<String> getLogs() {
        return logs
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    public String getLastLog() {
        return getLogs()
                .stream()
                .findFirst()
                .orElse(null);
    }
}