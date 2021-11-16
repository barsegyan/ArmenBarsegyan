package selenium_hw_5.steps;

import selenium_hw_3.data.UserData;
import selenium_hw_3.elements.UserLine;
import selenium_hw_5.context.TestContext;
import selenium_hw_5.util.AssertionUtil;
import selenium_hw_5.util.UserTableUtil;
import io.cucumber.java.en.Then;
import org.apache.commons.configuration.Configuration;
import org.assertj.core.api.SoftAssertions;
import org.openqa.selenium.WebElement;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class AssertionStep extends AbstractStep {

    @Then("Browser title equals {string}")
    public void comparePageTitle(String expectedTitle) {
        assertThat(homePage.getTitle())
                .isEqualTo(expectedTitle);
    }

    @Then("Name is displayed and equals to {string}")
    public void compareUserNameDisplayed(String expectedName) {
        assertThat(homePage.getHeader().getName())
                .isEqualTo(expectedName);
    }

    @Then("I see that part of logs in right section on Different Elements page")
    public void assertLogsInRightSectionOnDifferentElementsPageIsEqualTo(List<String> expectedLogs) {
        SoftAssertions softAssertions = new SoftAssertions();
        List<String> actualLogs = differentElementsPage.getRightSection().getLogs();
        softAssertions.assertThat(actualLogs)
                .hasSize(expectedLogs.size());
        Iterator<String> expected = expectedLogs.iterator();
        for (String actualLog : actualLogs) {
            String regexForString = AssertionUtil.getLogRegexWithTime(expected.next());
            softAssertions.assertThat(actualLog)
                    .matches(regexForString);
        }
        softAssertions.assertAll();
    }

    @Then("1 log row has {string} text in log section")
    public void assertLastLogInRightSectionOnUserTablePageIsEqualTo(String expectedLog) {
        String actualLog = userTablePage.getRightSection().getLastLog();
        String regexForString = AssertionUtil.getLogRegexWithTime(expectedLog);
        assertThat(actualLog)
                .matches(regexForString);
    }

    @Then("{string} page should be opened")
    public void assertThatPageIsOpened(String pageName) {
        Configuration properties = TestContext.getInstance().getTestObject("properties");
        assertThat(driver.getCurrentUrl())
                .isEqualTo(properties.getString(pageName));
    }

    @Then("{int} Number Type Dropdowns should be displayed on Users Table on User Table Page")
    public void assertNumberAndVisibilityOfDropDownOnUserTablePage(int numberOfDropDowns) {
        List<WebElement> dropDowns = UserTableUtil.getFromUserTableByLambda(
                userTablePage.getUserTable(),
                userLine -> userLine.getVipCheckBox().getWrappedElement()
        );
        AssertionUtil.assertNumberAndVisibility(dropDowns, numberOfDropDowns);
    }

    @Then("{int} Usernames should be displayed on Users Table on User Table Page")
    public void assertNumberAndVisibilityOfUsernamesOnUserTablePage(int numberOfUserNames) {
        List<WebElement> userNames = UserTableUtil.getFromUserTableByLambda(
                userTablePage.getUserTable(),
                UserLine::getUserName
        );
        AssertionUtil.assertNumberAndVisibility(userNames, numberOfUserNames);
    }

    @Then("{int} Description texts under images should be displayed on Users Table on User Table Page")
    public void assertNumberAndVisibilityOfDescriptionTextsOnUserTablePage(int numberOfText) {
        List<WebElement> text = UserTableUtil.getFromUserTableByLambda(
                userTablePage.getUserTable(),
                UserLine::getDescriptionText
        );
        AssertionUtil.assertNumberAndVisibility(text, numberOfText);
    }

    @Then("{int} checkboxes should be displayed on Users Table on User Table Page")
    public void assertNumberAndVisibilityOfCheckBoxesOnUserTablePage(int numberOfCheckBoxes) {
        List<WebElement> checkBoxes = UserTableUtil.getFromUserTableByLambda(
                userTablePage.getUserTable(),
                UserLine::getVipCheckBox
        );
        AssertionUtil.assertNumberAndVisibility(checkBoxes, numberOfCheckBoxes);
    }

    @Then("User table should contain following values:")
    public void checkCheckboxes(List<List<String>> dataTable) {
        dataTable.remove(0);
        assertThat(userTablePage.getUserTable().createUserTable()).isEqualTo(dataTable);
    }

    @Then("droplist should contain values in column Type for user {string}")
    public void assertDropDownValuesForUserOnUserTablePage(
            String userName,
            List<String> expectedDropDownValues) {
        List<String> dropDownForUserOptions = userTablePage
                .getUserTable()
                .getUsersInTable()
                .stream()
                .filter(userLine -> Objects.equals(userLine.getUserName().getText(), userName))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Can't find user in table: " + userName))
                .getDropDown()
                .getOptions()
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());

        assertThat(dropDownForUserOptions)
                .isEqualTo(AssertionUtil.removeFirstFromList(expectedDropDownValues));
    }
}
