package tests;

import base.BaseTest;
import config.Config;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.RegisterPage;

import java.time.Duration;

/**
 * Module: Registration
 * Maps to test cases FR_5–FR_6 in the Test Plan document.
 */
public class RegisterTest extends BaseTest {

    // FR_5 | Priority: High | A brand-new student account can register and is sent to login.php
    @Test
    public void testFR_5_validStudentRegistration() {
        RegisterPage registerPage = new RegisterPage(driver).open();
        String uniqueEmail = "student_" + System.currentTimeMillis() + "@library.com";

        registerPage.register("Test Student", uniqueEmail, "Password123", "student");

        new WebDriverWait(driver, Duration.ofSeconds(Config.EXPLICIT_WAIT_SECONDS))
                .until(ExpectedConditions.urlContains("login.php"));

        Assert.assertTrue(driver.getCurrentUrl().contains("login.php"),
                "Successful registration should redirect to login.php");
    }

    // FR_6 | Priority: Medium | Registering with an email that already exists is rejected
    @Test
    public void testFR_6_duplicateEmailRegistration() {
        RegisterPage registerPage = new RegisterPage(driver).open();

        // admin@library.com is guaranteed to exist (seeded in l_db.sql)
        registerPage.register("Duplicate User", Config.ADMIN_EMAIL, "Password123", "student");

        Assert.assertEquals(registerPage.getErrorMessage(), "This email is already registered.");
    }
}
