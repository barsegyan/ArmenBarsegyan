package selenium_hw_3.ex2;

import org.apache.commons.configuration.Configuration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class DiffPageInput {

    public static WebElement getCheckBoxByName(WebDriver driver, String name) {
        Optional<WebElement> checkBox = driver
                .findElements(By.className("label-checkbox"))
                .stream()
                .filter(elem -> elem.getText().contains(name))
                .findFirst();
        if (!checkBox.isPresent()) {
            Assert.fail("Can't find checkBox by name: " + name);
        }
        return checkBox.get().findElement(By.tagName("input"));

    }

    public static WebElement getRadioButtonByName(WebDriver driver, String name) {
        Optional<WebElement> radioButton = driver
                .findElements(By.className("label-radio"))
                .stream()
                .filter(elem -> elem.getText().contains(name))
                .findFirst();
        if (!radioButton.isPresent()) {
            Assert.fail("Can't find radioButton by name: " + name);
        }
        return radioButton.get().findElement(By.tagName("input"));
    }

    public static void checkAllCheckBoxLogs(WebDriver driver, boolean expectedState, Configuration properties) {
        for (Object checkBoxNameObj : properties.getList("checkbox")) {
            String checkBoxName = (String) checkBoxNameObj;
            getCheckBoxByName(driver, checkBoxName).click();
            List<WebElement> logs = driver.findElements(By.cssSelector(".panel-body-list>li"));
            String actualLog = logs.get(0).getText();
            assertThat(actualLog)
                    .contains(checkBoxName + properties.getString("checkbox.log") + " " + expectedState);
        }
    }

}
