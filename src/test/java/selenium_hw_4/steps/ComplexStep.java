package selenium_hw_4.steps;

import selenium_hw_3.pages.FramePage;
import selenium_hw_3.pages.HomePage;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import java.util.List;

public class ComplexStep extends AbstractStep {

    ActionStep actionStep;
    AssertionStep assertionStep;

    public ComplexStep(WebDriver driver, ActionStep actionStep, AssertionStep assertionStep) {
        super(driver);
        this.actionStep = actionStep;
        this.assertionStep = assertionStep;
    }

    @Step("Switch to the iframe and check that there is 'Frame Button' in the iframe, then switch to original window back")
    public void switchToFrameAndCheckButtonThenSwitchBack() {
        FramePage framePage = homePage.switchToFrame();
        assertThat(framePage.isFrameButtonDisplayed())
                .isTrue();
        homePage = (HomePage) framePage.switchToDefault();
    }

    @Step("Assert that checkboxes '{checkBoxesNames}' are clickable")
    public void assertThatCheckBoxesAreClickable(List<Object> checkBoxesNames) {
        for (Object checkBoxName : checkBoxesNames) {
            actionStep.selectCheckBox((String) checkBoxName);
            assertionStep.assertCheckboxIsSelected((String) checkBoxName);
        }
    }

    @Step("Assert that radio buttons '{checkBoxesNames}' are clickable")
    public void assertThatRadioButtonsAreClickable(List<Object> radioButtonsNames) {
        for (Object radioButtonsName : radioButtonsNames) {
            actionStep.selectRadio((String) radioButtonsName);
            assertionStep.assertRadioIsSelected((String) radioButtonsName);
        }
    }

    @Step("Assert that there is an individual log row and value is corresponded to the status of checkbox")
    public void assertThatCheckBoxesHaveIndividualLog(List<Object> checkBoxesNames) {
        for (Object checkBoxName : checkBoxesNames) {
            for (Boolean expectedState : new Boolean[] {true, false}) {
                actionStep.selectCheckBox((String) checkBoxName);
                assertionStep.assertLastLogIsEqualTo(checkBoxName + ": condition changed to " + expectedState);
            }
        }
    }

    @Step("Assert that there is an individual log row and value is corresponded to the status of radio button")
    public void assertThatRadioButtonsHaveIndividualLog(List<Object> radioButtonsNames) {
        for (Object radioName : radioButtonsNames) {
            actionStep.selectRadio((String) radioName);
            assertionStep.assertLastLogIsEqualTo("metal: value changed to " + radioName);
        }
    }

    @Step("Assert that for dropdown there is a log row and value is corresponded to the selected value")
    public void assertThatDropDownHaveIndividualLog() {
        Select dropdown = differentElementsPage.getMain().getDropDown();
        for (WebElement option : dropdown.getOptions()) {
            actionStep.selectInDropDown(option.getText());
            assertionStep.assertLastLogIsEqualTo("Colors: value changed to " + option.getText());
        }
    }

    @Step("Assert that for dropdown there is a log row and value is corresponded to the selected value")
    public void assertThatLogsForMainIsCorresponded(List<Object> checkBoxesNames, List<Object> radioButtonsNames) {
        assertThatCheckBoxesHaveIndividualLog(checkBoxesNames);
        assertThatRadioButtonsHaveIndividualLog(radioButtonsNames);
        assertThatDropDownHaveIndividualLog();
    }

}
