package selenium_hw_4.steps;

import selenium_hw_3.pages.DifferentElementsPage;
import selenium_hw_3.pages.HomePage;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;
import ru.yandex.qatools.htmlelements.element.CheckBox;
import ru.yandex.qatools.htmlelements.element.Radio;

public class ActionStep extends AbstractStep {

    public ActionStep(WebDriver driver) {
        super(driver);
    }

    @Step("Open Home Page")
    public void openHomePage() {
        homePage = new HomePage(driver);
    }

    @Step("Perform login| {login} : {password}")
    public void performLogin(String login, String password) {
        homePage.getHeader().signInWithoutClear(login,password);
    }

    @Step("Close Home page")
    public void closeHomePage() {
        homePage.close();
    }

    @Step("Close Different Elements page")
    public void closeDifferentElementsPage() {
        differentElementsPage.close();
    }

    @Step("Open through the header menu Service -> Different Elements Page ")
    public void openDifferentElementsPageThroughHeaderMenu() {
        DifferentElementsPage differentElementsPage = homePage.getHeader()
                .clickService()
                .clickDifferentElement();
    }

    @Step("Select {colorOptionName} in dropdown")
    public void selectInDropDown(String colorOptionName) {
        Select dropdown = differentElementsPage.getMain().getDropDown();
        dropdown.selectByVisibleText(colorOptionName);
    }

    @Step("Select checkbox '{checkBoxName}'")
    public void selectCheckBox(String checkBoxName) {
        CheckBox checkBox = differentElementsPage.getMain().getCheckBox(checkBoxName);
        checkBox.click();
    }

    @Step("Select radio '{radioName}'")
    public void selectRadio(String radioName) {
        Radio radio = differentElementsPage.getMain().getRadio(radioName);
        radio.click();
    }

    @Step("Clear checkBoxes")
    public void clearCheckBoxes() {
        differentElementsPage.getMain().clearCheckBoxes();
    }

}
