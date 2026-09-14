# CS-Shelf (online-library) — Selenium Test Automation

Maven + Java + Selenium WebDriver + TestNG, Page Object Model.
Built against the PHP app running locally under XAMPP.

## Prerequisites

- **JDK 17+** — check with `java -version`
- **Maven** — check with `mvn -version`
- **Google Chrome** installed (WebDriverManager downloads the matching
  driver automatically — you don't need to download chromedriver.exe
  yourself)
- XAMPP running (Apache + MySQL), with the app imported and reachable
  in your browser

## 1. Confirm your base URL

Open `src/test/java/config/Config.java` and check `BASE_URL` matches
where your project actually lives. From your screenshots, it's:

```java
public static final String BASE_URL = "http://localhost/online-library/";
```

If your htdocs folder is ever named differently
(e.g. `online-library-system`), update this line **and** the
`BASE_PATH` constant in the PHP app's `config/database.php` so they
match.

## 2. Open in VS Code

1. Open this folder (`selenium-project`) in VS Code.
2. Install the **"Extension Pack for Java"** extension if you don't
   already have it — VS Code will then recognize `pom.xml` and resolve
   dependencies automatically.
3. Wait for the bottom-right "Java: Loading..." indicator to finish
   and for the `Testing` icon (flask) to appear in the left sidebar.

## 3. Run the tests

**From the VS Code Testing sidebar:** click the flask icon, then the
▶ run-all button — it will discover `LoginTest` and `RegisterTest`
automatically.

**From a terminal**, inside this folder:

```bash
mvn clean test
```

A Chrome window will open and drive itself through each test. Results
print in the terminal, and a full report is written to
`target/surefire-reports/`.

## What's covered so far

| Test Case ID | Module | What it checks |
|---|---|---|
| FR_1 | Authentication | Valid admin login redirects to `admin/index.php` |
| FR_2 | Authentication | Wrong password shows the correct error and stays on login |
| FR_3 | Authentication | Unregistered email shows "No account found with that email." |
| FR_4 | Authentication | Empty submission is blocked client-side |
| FR_5 | Registration | New student registers successfully → redirected to login |
| FR_6 | Registration | Duplicate email is rejected |
| FR_7 | Borrow Lifecycle | Admin approves a pending request → status becomes Approved |
| FR_8 | Borrow Lifecycle | Admin declines a pending request → status becomes Declined |
| FR_9 | Borrow Lifecycle | Admin marks an approved request as Returned |

**Before running FR_7–FR_9**, make sure at least 2-3 books exist in the
catalog (Admin → Manage Books). Each test registers its own throwaway
student and borrows whichever book is first available, so it needs
real inventory to work with — if every book is already borrowed, the
test fails with a clear "no available book" message rather than a
confusing Selenium error.

## Next steps

- Add page objects + tests for: teacher recommend/review, manage
  users (activate/deactivate), student history/reviews.
- Feed the `Actual Result` / `Pass`/`Fail` columns of each run into the
  test case tables in the official Test Plan document.
- Once things are stable, consider adding a `BeforeSuite` that seeds a
  known student/teacher account directly via the DB, so tests don't
  depend on state left over from prior runs.
