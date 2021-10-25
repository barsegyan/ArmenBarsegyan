package selenium_hw_3.pages;

import org.openqa.selenium.By;

public class FramePage {

    private static final By buttonLocator = By.id("frame-button");

    private BasePage parent;

    public FramePage(BasePage parent) {
        this.parent = parent;
    }

    public boolean isFrameButtonDisplayed() {
        return parent.driver.findElement(buttonLocator).isDisplayed();
    }

    public BasePage switchToDefault() {
        parent.driver.switchTo().parentFrame();
        return parent;
    }
}
