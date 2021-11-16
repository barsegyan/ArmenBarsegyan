package selenium_hw_4.tests.ex1;

import selenium_hw_4.listeners.ScreenShotListener;
import selenium_hw_4.tests.BaseTest;
import io.qameta.allure.Story;
import org.assertj.core.api.SoftAssertions;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

@Listeners({ScreenShotListener.class})
@Story("Show that screen shot listener is working so there are screenshots while failse")
public class HomePageTestFailed extends BaseTest {

    private List<String> textUnderImages;

    @BeforeMethod(description = "Setup data for test")
    public void setUpData() {
        textUnderImages = Arrays.asList(
                properties.getString("images.text.1"),
                properties.getString("images.text.2"),
                properties.getString("images.text.3"),
                properties.getString("images.text.4")
        );
    }

    @Test(description = "Test screenshot listener")
    public void MainItemPlaceAndVisibleTest() {

        SoftAssertions softAssertions = new SoftAssertions();

        // 1. Open test site by URL
        actionStep.openHomePage();

        // 2. Assert Browser title
        assertionStep.comparePageTitle(properties.getString("title"));

        // 3. Perform login
        actionStep.performLogin(properties.getString("login"), properties.getString("password"));
        assertionStep.verifyIsLogged();

        // 4. Assert Username is logged
        assertionStep.compareUserNameDisplayed(properties.getString("name"));

        // 5. Assert that there are 4 items on the header section are displayed and they have proper texts
        assertionStep.assertNumberAndNamesOfItemsInHeader(
                properties.getInt("toolbar.size"),
                properties.getList("toolbar")
        );

        // 6. Assert that there are 4 images on the Index Page and they are displayed
        assertionStep.assertNumberOfBenefitImagesAndCheckIfDisplayed(properties.getInt("images.size"));
        Assert.fail("Manually fail");
        // 7. Assert that there are 4 texts on the Index Page under icons and they have proper text
        assertionStep.assertNumberOfBenefitTextAndCheckText(
                properties.getInt("text.size"),
                textUnderImages
        );

        // 8. Assert that there is the iframe with "Frame Button" exist
        assertionStep.assertThereIsFrame();

        // 9.  Switch to the iframe and check that there is "Frame Button" in the iframe
        // 10. Switch to original window back
        complexStep.switchToFrameAndCheckButtonThenSwitchBack();

        // 11. Assert that there are 5 items in the Left Section are displayed and they have proper text
        assertionStep.assertNumberOfItemsInLeftSectionAndCheckTextInThem(
                properties.getInt("text.left.size"),
                properties.getList("text.left")
        );

        // 12. Close Browser
        actionStep.closeHomePage();
    }
}
