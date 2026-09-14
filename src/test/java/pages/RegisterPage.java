package pages;

import config.Config;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class RegisterPage {

    private final WebDriver driver;

    private final By fullNameField = By.name("full_name");
    private final By emailField = By.name("email");
    private final By passwordField = By.name("password");
    private final By roleDropdown = By.name("role");
    private final By submitButton = By.cssSelector("input[type='submit']");
    private final By errorMessage = By.className("alert-danger");

    public RegisterPage(WebDriver driver) {
        this.driver = driver;
    }

    public RegisterPage open() {
        driver.get(Config.BASE_URL + "register.php");
        return this;
    }

    /**
     * @param role must be exactly "student" or "teacher" — those are the
     *             only <option value="..."> entries the form accepts.
     */
    public void register(String fullName, String email, String password, String role) {
        driver.findElement(fullNameField).sendKeys(fullName);
        driver.findElement(emailField).sendKeys(email);
        driver.findElement(passwordField).sendKeys(password);
        new Select(driver.findElement(roleDropdown)).selectByValue(role);
        driver.findElement(submitButton).click();
    }

    public String getErrorMessage() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(Config.EXPLICIT_WAIT_SECONDS));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessage)).getText();
    }
}
