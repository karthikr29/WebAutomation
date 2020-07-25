package Base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class BaseTestClass {
    public static WebDriver driver;

    @BeforeSuite
    public void setup() throws IOException {
        initializeDriver(getBrowserName());
    }

    @AfterSuite
    public void cleanUp() {
        driver.quit();
    }

    public void openHomepage() {
        driver.get(getUrl());
    }

    /**
     * Initializes the WebDriver with Chrome/Firefox/HTMLUnitDriver
     * @param browserName Name/type of the browser to initialize to perform testing
     */
    private void initializeDriver(String browserName) {
        String userDirectory = System.getProperty("user.dir");
        switch (browserName) {
            case "chrome":
                if (System.getProperty("os.name").toLowerCase().contains("mac"))
                    System.setProperty("webdriver.chrome.driver", userDirectory + "/src/main/resources/drivers/chromedriver");
                else if(System.getProperty("os.name").toLowerCase().contains("win"))
                    System.setProperty("webdriver.chrome.driver", userDirectory + "\\src\\main\\resources\\drivers\\chromedriver.exe");
                driver = new ChromeDriver();
                break;
            case "firefox":
                if (System.getProperty("os.name").toLowerCase().contains("mac"))
                    System.setProperty("webdriver.gecko.driver", userDirectory + "/src/main/resources/drivers/geckodriver");
                else if(System.getProperty("os.name").toLowerCase().contains("win"))
                    System.setProperty("webdriver.chrome.driver", userDirectory + "\\src\\main\\resources\\drivers\\geckodriver.exe");
                driver = new FirefoxDriver();
                break;
            case "headless":
                if (System.getProperty("os.name").toLowerCase().contains("mac"))
                    System.setProperty("webdriver.chrome.driver", userDirectory + "/src/main/resources/drivers/chromedriver");
                else if(System.getProperty("os.name").toLowerCase().contains("win"))
                    System.setProperty("webdriver.chrome.driver", userDirectory + "\\src\\main\\resources\\drivers\\chromedriver.exe");
                ChromeOptions options = new ChromeOptions();
                options.setHeadless(true);
                driver = new ChromeDriver(options);
                break;
            default:
                throw new IllegalArgumentException("Invalid browser name: " + browserName);
        }
    }

    /**
     * Fetches browser name from config.properties file
     * @return browser name from config.properties file
     */
    private String getBrowserName() throws IOException {
        return getPropertyValue("browserName");
    }

    /**
     * Fetches the URL under test from config.properties
     * @return URL under test from config.properties
     */
    private String getUrl()  {
        try {
            return getPropertyValue("url");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Fetches property value from config.properties file
     */
    private String getPropertyValue(String propertyKey) throws IOException {
        String fileName = "config/config.properties";
        Properties properties = new Properties();
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName);
        if (inputStream != null) {
            properties.load(inputStream);
        } else  {
            throw new FileNotFoundException("Property File: " + fileName);
        }
        String propertyValue = properties.getProperty(propertyKey).toLowerCase();

        return propertyValue;
    }

}
