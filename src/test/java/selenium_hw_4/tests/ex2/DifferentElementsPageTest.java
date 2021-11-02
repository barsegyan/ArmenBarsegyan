package selenium_hw_4.tests.ex2;

import selenium_hw_4.listeners.ScreenShotListener;
import selenium_hw_4.tests.BaseTest;
import io.qameta.allure.Feature;
import org.assertj.core.api.SoftAssertions;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.util.Arrays;

@Listeners({ ScreenShotListener.class })
@Feature("Allow user click on elements(checkboxes, radio buttons, dropdown) in main element of Different Elements page and see logs")
public class DifferentElementsPageTest extends BaseTest {

    @Test(description = "Test checkboxes, radio buttons, dropdown and logs of them while logged")
    public void FormAndLogsTest() {
        SoftAssertions softAssertions = new SoftAssertions();

        // 1. Open test site by URL
        actionStep.openHomePage();

        // 2. Assert Browser title
        assertionStep.comparePageTitle(properties.getString("title"));

        // 3. Perform login
        actionStep.performLogin(properties.getString("login"),properties.getString("password"));
        assertionStep.verifyIsLogged();

        // 4. Assert Username is logged
        assertionStep.compareUserNameDisplayed(properties.getString("name"));

        // 5. Open through the header menu Service -> Different Elements Page
        actionStep.openDifferentElementsPageThroughHeaderMenu();
        assertionStep.compareCurrentUrl(properties.getString("diffURL"));

        // 6. Select checkboxes
        complexStep.assertThatCheckBoxesAreClickable(Arrays.asList("Water", "Wind"));

        // 7. Select radio
        complexStep.assertThatRadioButtonsAreClickable(Arrays.asList("Selen"));

        // 8. Select in dropdown
        actionStep.selectInDropDown("Yellow");
        assertionStep.assertThatOptionInDropDownIsSelected("Yellow");

        /**
         * 9. Assert that
         * - for each checkbox there is an individual log row and value is corresponded to the status of checkbox
         * - for radio button there is a log row and value is corresponded to the status of radio button
         * - for dropdown there is a log row and value is corresponded to the selected value.
         */
        actionStep.clearCheckBoxes();
        complexStep.assertThatLogsForMainIsCorresponded(
                properties.getList("checkbox"),
                properties.getList("radio")
        );

        // 10. Close Browser
        actionStep.closeDifferentElementsPage();
    }
}
