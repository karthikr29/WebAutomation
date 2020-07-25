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
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;


public class BaseTestClass {
    public static WebDriver driver;
    public Helper helper;


    @BeforeSuite(alwaysRun = true)
    public void initialize() throws IOException {
        initializeDriver(getBrowserName());
        helper = new Helper(driver);
    }

    @AfterSuite(alwaysRun = true)
    public void closeSession() {
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
        String chromeDriverMacPath = "/src/main/resources/drivers/chromedriver";
        String firefoxDriverMacPath = "/src/main/resources/drivers/geckodriver";
        String chromeDriverWinPath = "\\src\\main\\resources\\drivers\\chromedriver.exe";
        String firefoxDriverWinPath = "\\src\\main\\resources\\drivers\\geckodriver.exe";
        switch (browserName) {
            case "chrome":
                if (System.getProperty("os.name").toLowerCase().contains("mac"))
                    System.setProperty("webdriver.chrome.driver", userDirectory + chromeDriverMacPath);
                else if(System.getProperty("os.name").toLowerCase().contains("win"))
                    System.setProperty("webdriver.chrome.driver", userDirectory + chromeDriverWinPath);
                driver = new ChromeDriver();
                break;
            case "firefox":
                if (System.getProperty("os.name").toLowerCase().contains("mac"))
                    System.setProperty("webdriver.gecko.driver", userDirectory + firefoxDriverMacPath);
                else if(System.getProperty("os.name").toLowerCase().contains("win"))
                    System.setProperty("webdriver.chrome.driver", userDirectory + firefoxDriverWinPath);
                driver = new FirefoxDriver();
                break;
            case "headless":
                if (System.getProperty("os.name").toLowerCase().contains("mac"))
                    System.setProperty("webdriver.chrome.driver", userDirectory + chromeDriverMacPath);
                else if(System.getProperty("os.name").toLowerCase().contains("win"))
                    System.setProperty("webdriver.chrome.driver", userDirectory + chromeDriverWinPath);
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

    /**
     * Note implemented properly
     * TODO make it work properly
     */
    public Object page(Class<?> className) {
        try {
            Class<?> cl = Class.forName(className.toString());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Class<?>[] type = {Helper.class};
        Constructor<Class> constructor = null;

        try {
            constructor = (Constructor<Class>) className.getConstructor(type);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        try {
            return constructor.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

}
