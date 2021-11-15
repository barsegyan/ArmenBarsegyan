package selenium_hw_3.elements;

import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ru.yandex.qatools.htmlelements.element.HtmlElement;

import java.util.ArrayList;
import java.util.List;

@FindBy(className = "main-content-hg")
public class UserTable extends HtmlElement {

    @FindBy(xpath = "//tr/td[1]")
    private List<WebElement> numberTypeDropdowns;

    @FindBy(css = "td>div>input")
    private List<WebElement> checkboxes;

    @FindBy(css = "td>div>span")
    private List<WebElement> description;

    @FindBy(css = "td>a")
    private List<WebElement> userNames;

    @FindBy(css = "td>select")
    private List<WebElement> dropdowns;

    @FindBy(css = ".main-content-hg th")

    @Getter
    private List<WebElement> columnNames;

    @Getter
    private List<UserLine> usersInTable;


    public List<WebElement> getUserNames() {
        return userNames;
    }

    public List<WebElement> getCheckboxes() {
        return checkboxes;
    }

    public List<WebElement> getNumberTypeDropdowns() {
        return numberTypeDropdowns;
    }

    public List<List<String>> createUserTable() {
        List<List<String>> userTable = new ArrayList<>();

        for (int i = 0; i < numberTypeDropdowns.size(); i++) {
            userTable.add(new ArrayList<>(
                    List.of(numberTypeDropdowns.get(i).getText(),
                            userNames.get(i).getText(),
                            description.get(i).getText().replace("\n", " "))));
        }

        return userTable;
    }
}
