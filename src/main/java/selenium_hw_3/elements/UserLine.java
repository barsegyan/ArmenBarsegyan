package selenium_hw_3.elements;

import selenium_hw_3.data.UserData;
import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ru.yandex.qatools.htmlelements.element.CheckBox;
import ru.yandex.qatools.htmlelements.element.HtmlElement;
import ru.yandex.qatools.htmlelements.element.Select;

import java.util.Arrays;

@FindBy(css = "tbody tr")
public class UserLine extends HtmlElement {

    @FindBy(xpath = ".//td[1]")
    WebElement number;

    @FindBy(tagName = "select")
    @Getter Select dropDown;

    @FindBy(tagName = "a")
    @Getter WebElement userName;

    @FindBy(tagName = "input")
    @Getter CheckBox vipCheckBox;

    @FindBy(tagName = "span")
    @Getter WebElement descriptionText;

    public UserData getUserData() {
        return new UserData(Arrays.asList(
                number.getText(),
                userName.getText(),
                descriptionText.getText())
        );
    }

}
