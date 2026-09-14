package pages;

import config.Config;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class BrowseBooksPage {

    private final WebDriver driver;
    private final By bookRows = By.cssSelector("table tbody tr");

    public BrowseBooksPage(WebDriver driver) {
        this.driver = driver;
    }

    public BrowseBooksPage open() {
        driver.get(Config.BASE_URL + "student/browse_books.php");
        return this;
    }

    /**
     * Finds the first row still marked "Available", clicks its
     * "Request to Borrow" link, and returns that book's title so the
     * caller (e.g. an admin-side assertion) can locate the same request.
     */
    public String requestToBorrowFirstAvailableBook() {
        List<WebElement> rows = driver.findElements(bookRows);
        for (WebElement row : rows) {
            if (row.getText().contains("Available")) {
                String title = row.findElement(By.xpath("./td[1]")).getText();
                row.findElement(By.linkText("Request to Borrow")).click();
                return title;
            }
        }
        throw new NoSuchElementException(
                "No available book found — add at least 2-3 books via admin/manage_books.php before running this suite.");
    }
}
