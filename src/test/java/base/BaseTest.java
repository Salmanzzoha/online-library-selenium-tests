package base;

import config.Config;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import utils.DriverFactory;

public class BaseTest {

    protected WebDriver driver;

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        driver = DriverFactory.getDriver();
        driver.get(Config.BASE_URL);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        DriverFactory.quitDriver();
    }
}
