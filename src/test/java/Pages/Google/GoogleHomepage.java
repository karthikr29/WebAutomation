package Pages.Google;

import Base.Helper;
import org.openqa.selenium.By;

public class GoogleHomepage  {
    Helper helper;
    public GoogleHomepage(Helper helper) {
        this.helper = helper;
    }

    private static final By homePageLogo = By.id("hplogo");
    private static final By searchBar = By.name("q");


    public void validateHomepageLogo() {
        helper.isDisplayed(homePageLogo);
    }

    public void validateSearchBar() {
        helper.isDisplayed(searchBar);
    }


}
