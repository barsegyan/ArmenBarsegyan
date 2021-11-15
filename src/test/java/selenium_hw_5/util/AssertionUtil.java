package selenium_hw_5.util;

import lombok.experimental.UtilityClass;
import org.assertj.core.api.SoftAssertions;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public final class AssertionUtil {
    final int boundarySize = 0;

    public static String getLogRegexWithTime(String logWithOutTime) {
        return String.format("\\d\\d:\\d\\d:\\d\\d %s", logWithOutTime);
    }

    public static void assertNumberAndVisibility(List<WebElement> elements, int expectedSize) {
        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(elements)
                .hasSize(expectedSize)
                .allMatch(WebElement::isDisplayed);
        softAssertions.assertAll();
    }

    public static <T> List<T> removeFirstFromList(List<T> list) {
        List<T> newList = new ArrayList<>(list);
        if (newList.size() > boundarySize) {
            newList.remove(boundarySize);
        }
        return newList;
    }
}
