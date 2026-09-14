package tests;

import base.BaseTest;
import config.Config;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;

import java.time.Duration;

/**
 * Module: Authentication
 * Maps to test cases FR_1–FR_4 in the Test Plan document.
 */
public class LoginTest extends BaseTest {

    // FR_1 | Priority: High | Valid admin login redirects to the admin dashboard
    @Test
    public void testFR_1_validAdminLogin() {
        LoginPage loginPage = new LoginPage(driver).open();
        loginPage.login(Config.ADMIN_EMAIL, Config.ADMIN_PASSWORD);

        new WebDriverWait(driver, Duration.ofSeconds(Config.EXPLICIT_WAIT_SECONDS))
                .until(ExpectedConditions.urlContains("admin/index.php"));

        Assert.assertTrue(driver.getCurrentUrl().contains("admin/index.php"),
                "Expected redirect to admin dashboard after valid login");
    }

    // FR_2 | Priority: High | Correct email, wrong password is rejected
    @Test
    public void testFR_2_invalidPassword() {
        LoginPage loginPage = new LoginPage(driver).open();
        loginPage.login(Config.ADMIN_EMAIL, "wrongPassword123");

        Assert.assertEquals(loginPage.getErrorMessage(), "The password you entered was not valid.");
        Assert.assertTrue(loginPage.isOnLoginPage(), "User should remain on the login page");
    }

    // FR_3 | Priority: Medium | Email that has never been registered is rejected
    @Test
    public void testFR_3_nonExistentEmail() {
        LoginPage loginPage = new LoginPage(driver).open();
        loginPage.login("no_such_user_" + System.currentTimeMillis() + "@library.com", "somePassword");

        Assert.assertEquals(loginPage.getErrorMessage(), "No account found with that email.");
    }

    // FR_4 | Priority: Low | Submitting the form with both fields empty does not log in
    @Test
    public void testFR_4_emptyCredentials() {
        LoginPage loginPage = new LoginPage(driver).open();
        loginPage.login("", "");

        // HTML5 "required" blocks submission client-side, so the browser
        // never leaves login.php.
        Assert.assertTrue(loginPage.isOnLoginPage(),
                "Empty submission should be blocked and stay on login.php");
    }
}
