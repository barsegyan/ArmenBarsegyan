package selenium_hw_3.elements;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class Benefit {

    private WebElement webElement;

    private static final By textLocator = By.className("benefit-txt");
    private static final By imageLocator = By.className("benefit-icon");

    private WebElement getBenefitTextWeb() {
        return webElement.findElement(textLocator);
    }

    public Benefit(WebElement webElement) {
        this.webElement = webElement;
    }

    public WebElement getIcon() {
        return webElement.findElement(imageLocator);
    }

    public String getBenefitText() {
        return getBenefitTextWeb().getText();
    }

    public boolean isIconDisplayed() {
        return getIcon().isDisplayed();
    }

    public boolean isBenefitTextDisplayed() {
        return getBenefitTextWeb().isDisplayed();
    }
}
