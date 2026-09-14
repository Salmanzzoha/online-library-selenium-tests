package pages;

import config.Config;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {

    private final WebDriver driver;

    // login.php has no ids — every field is located by its "name" attribute
    private final By emailField = By.name("email");
    private final By passwordField = By.name("password");
    private final By submitButton = By.cssSelector("input[type='submit']");
    private final By errorMessage = By.className("alert-danger");
    private final By registerLink = By.linkText("Sign up now");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public LoginPage open() {
        driver.get(Config.BASE_URL + "login.php");
        return this;
    }

    public void login(String email, String password) {
        driver.findElement(emailField).clear();
        driver.findElement(emailField).sendKeys(email);
        driver.findElement(passwordField).clear();
        driver.findElement(passwordField).sendKeys(password);
        driver.findElement(submitButton).click();
    }

    public String getErrorMessage() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(Config.EXPLICIT_WAIT_SECONDS));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessage)).getText();
    }

    public boolean isOnLoginPage() {
        return driver.getCurrentUrl().contains("login.php");
    }

    public RegisterPage goToRegister() {
        driver.findElement(registerLink).click();
        return new RegisterPage(driver);
    }
}
