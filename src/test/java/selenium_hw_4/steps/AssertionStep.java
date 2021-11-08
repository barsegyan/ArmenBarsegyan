package selenium_hw_4.steps;

import selenium_hw_3.elements.Benefit;
import io.qameta.allure.Step;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.Condition;
import org.assertj.core.api.SoftAssertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import ru.yandex.qatools.htmlelements.element.CheckBox;
import ru.yandex.qatools.htmlelements.element.Radio;

import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class AssertionStep extends AbstractStep {

    public AssertionStep(WebDriver driver) {
        super(driver);
    }

    @Step("Compare that Home page title equal to {title}")
    public void comparePageTitle(String title) {
        assertThat(homePage.getTitle())
                .isEqualTo(title);
    }

    @Step("Check successful logged")
    public void verifyIsLogged() {
        assertThat(homePage.getHeader().isNameDisplayed()).isTrue();
    }

    @Step("Compare actual and expected name: {name}")
    public void compareUserNameDisplayed(String name) {
        assertThat(homePage.getHeader().getName())
                .isEqualTo(name);
    }

    @Step("Assert that there are {expectedSize} items on header and they have proper name {expectedName}")
    public void assertNumberAndNamesOfItemsInHeader(int expectedSize, List<Object> expectedName) {
        assertThat(homePage.getHeader().getNavigationListText().stream().filter(Objects::nonNull).count())
                .isEqualTo(expectedSize);
        assertThat(homePage.getHeader().getNavigationListText())
                .isEqualTo(expectedName);
    }

    @Step("Assert that there are {expectedSizeImages} images on the Index Page and they are displayed")
    public void assertNumberOfBenefitImagesAndCheckIfDisplayed(int expectedSizeImages) {
        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions
                .assertThat(homePage.getBenefits())
                .hasSize(expectedSizeImages)
                .allMatch(Benefit::isIconDisplayed);
        softAssertions.assertAll();
    }

    @Step("Assert that there are {expectedSizeText} texts on the Index Page under icons and they have proper text: {properText}")
    public void assertNumberOfBenefitTextAndCheckText(int expectedSizeText, List<String> properText) {
        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions
                .assertThat(homePage.getBenefits())
                .hasSize(expectedSizeText)
                .allMatch(Benefit::isBenefitTextDisplayed)
                .map(Benefit::getBenefitText)
                .isEqualTo(properText);
        softAssertions.assertAll();
    }

    @Step("Assert that there is the iframe with 'Frame Button' exist")
    public void assertThereIsFrame() {
        assertThat(homePage.isThereFrame())
                .isTrue();
    }

    @Step("Assert that there are {expectedNumberOfItems} items in the Left Section are displayed and they have proper text: {properText}")
    public void assertNumberOfItemsInLeftSectionAndCheckTextInThem(int expectedNumberOfItems, List<Object> properText) {
        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions
                .assertThat(homePage.getLeftSection().getNavigationList())
                .are(new Condition<>(WebElement::isDisplayed, "all displayed"))
                .hasSize(expectedNumberOfItems);
        softAssertions
                .assertThat(homePage.getLeftSection().getNavigationNamesList())
                .isEqualTo(properText);
        softAssertions.assertAll();
    }

    @Step("Assert that current URL is equal to expected: {expectedURL}")
    public void compareCurrentUrl(String expectedURL) {
        assertThat(driver.getCurrentUrl())
                .isEqualTo(expectedURL);
    }

    @Step("Assert that option {colorOptionName} in DropDown is selected")
    public void assertThatOptionInDropDownIsSelected(String colorOptionName) {
        Assertions.assertThat(differentElementsPage.getMain().getDropDown().getAllSelectedOptions())
                .hasSize(1)
                .map(WebElement::getText)
                .first()
                .isEqualTo(colorOptionName);
    }

    @Step("Assert that checkbox {checkBoxName} is selected")
    public void assertCheckboxIsSelected(String checkBoxName) {
        CheckBox checkBox = differentElementsPage.getMain().getCheckBox(checkBoxName);
        Assertions.assertThat(checkBox.isSelected()).isTrue();
    }

    @Step("Assert that radio {radioName} it's selected")
    public void assertRadioIsSelected(String radioName) {
        Radio radio = differentElementsPage.getMain().getRadio(radioName);
        Assertions.assertThat(radio.isSelected()).isTrue();
    }

    @Step("Assert that last log record is equal to {expectedLog}")
    public void assertLastLogIsEqualTo(String expectedLog) {
        assertThat(differentElementsPage.getRightSection().getLastLog())
                .contains(expectedLog);
    }

}
