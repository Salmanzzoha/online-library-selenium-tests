package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage {

    private final WebDriver driver;

    private final By loginLink = By.linkText("Login");
    private final By registerLink = By.linkText("Register");

    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    public LoginPage goToLogin() {
        driver.findElement(loginLink).click();
        return new LoginPage(driver);
    }

    public RegisterPage goToRegister() {
        driver.findElement(registerLink).click();
        return new RegisterPage(driver);
    }
}
