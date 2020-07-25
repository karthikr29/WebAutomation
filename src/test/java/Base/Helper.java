package Base;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class Helper {
    WebDriver driver;
    public Helper(WebDriver driver) {
        this.driver = driver;
    }

    public void clickAt(By element) {
        driver.findElement(element).click();
    }

    public void assertTrue(boolean value) {
        Assert.assertTrue(value);
    }

    public void isDisplayed(By element) {
        assertTrue(driver.findElement(element).isDisplayed());
    }
}
