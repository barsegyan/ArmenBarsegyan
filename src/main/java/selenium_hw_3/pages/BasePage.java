package selenium_hw_3.pages;

import org.openqa.selenium.WebDriver;

public abstract class BasePage {

    protected WebDriver driver;

    public String getTitle() {
        return driver.getTitle();
    }

    public void close() {
        driver.close();
    }

    public String getURL() {
        return driver.getCurrentUrl();
    }
}
