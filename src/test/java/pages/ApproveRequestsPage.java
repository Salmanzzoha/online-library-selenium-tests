package pages;

import config.Config;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ApproveRequestsPage {

    private final WebDriver driver;
    private final By requestRows = By.cssSelector("table tbody tr");

    public ApproveRequestsPage(WebDriver driver) {
        this.driver = driver;
    }

    public ApproveRequestsPage open() {
        driver.get(Config.BASE_URL + "admin/approve_requests.php");
        return this;
    }

    /**
     * Rows have no ids — a request is only identifiable by the student
     * name + book title text it contains, so we scan for the row that
     * contains both.
     */
    private WebElement findRow(String studentName, String bookTitle) {
        for (WebElement row : driver.findElements(requestRows)) {
            String text = row.getText();
            if (text.contains(studentName) && text.contains(bookTitle)) {
                return row;
            }
        }
        throw new NoSuchElementException(
                "No request row found for student '" + studentName + "' / book '" + bookTitle + "'");
    }

    public String getStatus(String studentName, String bookTitle) {
        return findRow(studentName, bookTitle).findElement(By.cssSelector("span.status")).getText();
    }

    public void approve(String studentName, String bookTitle) {
        findRow(studentName, bookTitle).findElement(By.linkText("Approve")).click();
        waitForReload();
    }

    public void decline(String studentName, String bookTitle) {
        findRow(studentName, bookTitle).findElement(By.linkText("Decline")).click();
        waitForReload();
    }

    public void markReturned(String studentName, String bookTitle) {
        findRow(studentName, bookTitle).findElement(By.linkText("Mark as Returned")).click();
        waitForReload();
    }

    private void waitForReload() {
        new WebDriverWait(driver, Duration.ofSeconds(Config.EXPLICIT_WAIT_SECONDS))
                .until(ExpectedConditions.urlContains("approve_requests.php"));
    }
}
