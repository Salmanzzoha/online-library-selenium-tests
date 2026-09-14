package tests;

import base.BaseTest;
import config.Config;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.ApproveRequestsPage;
import pages.BrowseBooksPage;
import pages.LoginPage;
import pages.RegisterPage;

import java.time.Duration;

/**
 * Module: Borrow Request Lifecycle (cross-role: student creates the
 * request, admin acts on it). Maps to test cases FR_7-FR_9 in the
 * Test Plan document.
 *
 * Requires at least 2-3 books already added via admin/manage_books.php
 * so each test has an available book to borrow.
 */
public class AdminApprovalTest extends BaseTest {

    // FR_7 | Priority: High | Admin approves a pending borrow request
    @Test
    public void testFR_7_adminApprovesRequest() {
        String studentName = "Approve Student";
        String bookTitle = createPendingRequestAsStudent(studentName);

        loginAsAdmin();
        ApproveRequestsPage approvals = new ApproveRequestsPage(driver).open();

        Assert.assertEquals(approvals.getStatus(studentName, bookTitle), "Pending",
                "Newly created request should start as Pending");

        approvals.approve(studentName, bookTitle);

        Assert.assertEquals(approvals.getStatus(studentName, bookTitle), "Approved",
                "Status should update to Approved after admin approves");
    }

    // FR_8 | Priority: High | Admin declines a pending borrow request
    @Test
    public void testFR_8_adminDeclinesRequest() {
        String studentName = "Decline Student";
        String bookTitle = createPendingRequestAsStudent(studentName);

        loginAsAdmin();
        ApproveRequestsPage approvals = new ApproveRequestsPage(driver).open();

        approvals.decline(studentName, bookTitle);

        Assert.assertEquals(approvals.getStatus(studentName, bookTitle), "Declined",
                "Status should update to Declined after admin declines");
    }

    // FR_9 | Priority: Medium | Admin marks an already-approved request as returned
    @Test
    public void testFR_9_adminMarksReturned() {
        String studentName = "Return Student";
        String bookTitle = createPendingRequestAsStudent(studentName);

        loginAsAdmin();
        ApproveRequestsPage approvals = new ApproveRequestsPage(driver).open();
        approvals.approve(studentName, bookTitle);
        approvals.markReturned(studentName, bookTitle);

        Assert.assertEquals(approvals.getStatus(studentName, bookTitle), "Returned",
                "Status should update to Returned after admin marks it returned");
    }

    /**
     * Registers a brand-new student, logs them in, and submits a borrow
     * request for the first available book. Returns the book's title so
     * the admin-side assertions can find the same row.
     */
    private String createPendingRequestAsStudent(String fullName) {
        String email = fullName.toLowerCase().replace(" ", "_") + "_" + System.currentTimeMillis() + "@library.com";
        String password = "Password123";

        RegisterPage registerPage = new RegisterPage(driver).open();
        registerPage.register(fullName, email, password, "student");

        // register.php redirects straight to login.php on success
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(email, password);
        new WebDriverWait(driver, Duration.ofSeconds(Config.EXPLICIT_WAIT_SECONDS))
                .until(ExpectedConditions.urlContains("student/index.php"));

        BrowseBooksPage browsePage = new BrowseBooksPage(driver).open();
        String bookTitle = browsePage.requestToBorrowFirstAvailableBook();

        driver.get(Config.BASE_URL + "logout.php");
        return bookTitle;
    }

    private void loginAsAdmin() {
        LoginPage loginPage = new LoginPage(driver).open();
        loginPage.login(Config.ADMIN_EMAIL, Config.ADMIN_PASSWORD);
        new WebDriverWait(driver, Duration.ofSeconds(Config.EXPLICIT_WAIT_SECONDS))
                .until(ExpectedConditions.urlContains("admin/index.php"));
    }
}
