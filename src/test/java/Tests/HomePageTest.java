package Tests;

import Base.BaseTestClass;
import Pages.Google.GoogleHomepage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class HomePageTest extends BaseTestClass {
    GoogleHomepage googleHomepage;

    @BeforeMethod
    public void setup() {
        googleHomepage = new GoogleHomepage(helper);
    }

    @Test
    public void testHomePage() {
        openHomepage();
        googleHomepage.validateHomepageLogo();
        googleHomepage.validateSearchBar();
    }
}
