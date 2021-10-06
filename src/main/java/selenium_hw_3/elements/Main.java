package selenium_hw_3.elements;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;
import ru.yandex.qatools.htmlelements.element.CheckBox;
import ru.yandex.qatools.htmlelements.element.HtmlElement;
import ru.yandex.qatools.htmlelements.element.Radio;

import java.util.List;
import java.util.Optional;

@FindBy(className = "main-content-hg")
public class Main extends HtmlElement {

    private static final By inputInLabelLocator = By.tagName("input");

    @FindBy(className = "label-checkbox")
    private List<WebElement> checkBoxes;

    @FindBy(className = "label-radio")
    private List<WebElement> radioButtons;

    @FindBy(css = ".label-checkbox > input")
    private List<CheckBox> checkBoxesInput;

    @FindBy(css = ".label-radio > input")
    private List<Radio> radioButtonsInput;

    @FindBy(tagName = "select")
    private WebElement dropDown;

    public Select getDropDown() {
        return new Select(dropDown);
    }

    public CheckBox getCheckBox(String label) {
        Optional<WebElement> checkBox = checkBoxes
                .stream()
                .filter(x -> x.getText().contains(label))
                .findFirst();
        return checkBox
                .map(webElement -> webElement.findElement(inputInLabelLocator))
                .map(CheckBox::new)
                .orElse(null);
    }

    public Radio getRadio(String label) {
        Optional<WebElement> radio = radioButtons
                .stream()
                .filter(x -> x.getText().contains(label))
                .findFirst();
        return radio
                .map(webElement -> webElement.findElement(inputInLabelLocator))
                .map(Radio::new)
                .orElse(null);
    }

    public void clearCheckBoxes() {
        checkBoxesInput.forEach(CheckBox::deselect);
    }
}
