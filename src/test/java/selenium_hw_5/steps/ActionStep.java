package selenium_hw_5.steps;

import selenium_hw_3.elements.UserLine;
import io.cucumber.java.en.When;
import org.openqa.selenium.support.ui.Select;
import ru.yandex.qatools.htmlelements.element.CheckBox;

import java.util.List;
import java.util.Objects;

public class ActionStep extends AbstractStep {

    @When("I click on {string} button in Header")
    public void clickOnHeaderButtonByName(String buttonOnHeaderName) {
        homePage.getHeader().getByNameInHeader(buttonOnHeaderName).click();
    }

    @When("I click on {string} button in Service dropdown")
    public void clickOnButtonInServiceDropDownByName(String buttonInServiceName) {
        homePage.getHeader().getByNameInServiceDropDownInHeader(buttonInServiceName).click();
    }

    @When("I select checkboxes in main form on Different Elements page")
    public void selectCheckBoxesInMainFormOnDifferentElementsPage(List<String> checkBoxesNames) {
        checkBoxesNames.forEach(name -> differentElementsPage.getMain().getCheckBox(name).click());
    }

    @When("I select radio buttons in main form on Different Elements page")
    public void selectRadioButtonInMainFormOnDifferentElementsPage(List<String> radioButtonsNames) {
        radioButtonsNames.forEach(name -> differentElementsPage.getMain().getRadio(name).click());
    }

    @When("I select in dropdown in main form on Different Elements page")
    public void selectDropDownInMainFormOnDifferentElementsPage(List<String> dropDownNames) {
        Select select = differentElementsPage.getMain().getDropDown();
        dropDownNames.forEach(select::selectByVisibleText);
    }

    @When("I select 'vip' checkbox for {string}")
    public void selectCheckBoxFor(String name) {
        userTablePage.getUserTable().getUsersInTable()
                .stream()
                .filter(userLine -> Objects.equals(userLine.getUserName().getText(), name))
                .map(UserLine::getVipCheckBox)
                .forEach(CheckBox::select);
    }

}
