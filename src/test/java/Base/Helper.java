package Base;

import org.openqa.selenium.By;

public class Helper extends BaseTestClass{

    public void clickAt(By element) {
        driver.findElement(element).click();
    }

}
