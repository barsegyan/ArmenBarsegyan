package selenium_hw_3.elements;

import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ru.yandex.qatools.htmlelements.element.HtmlElement;

import java.util.List;

@FindBy(className = "main-content-hg")
public class UserTable extends HtmlElement {

    @FindBy(css = ".main-content-hg th")
    @Getter private List<WebElement> columnNames;

    @Getter private List<UserLine> usersInTable;

}
