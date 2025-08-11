package tests;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.*;
import pages.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;



public class Calculator_Tests {
    private WebDriver driver;
    private LoginPage loginPage;
    private CarbohydrateCalculatorPage carbohydrateCalculatorPage;
    private ResultsPage resultsPage;
    private SaveCalculationPage saveCalculationPage;
    private MyAccountPage myAccountPage;
    private WebsiteHeaderPage websiteHeaderPage;

    @BeforeMethod
    public void setup() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--disable-cache");
        options.addArguments("--incognito");
        driver = new ChromeDriver(options);
        driver.manage().deleteAllCookies();
        driver.manage().window().maximize();

        loginPage = new LoginPage(driver);
        carbohydrateCalculatorPage = new CarbohydrateCalculatorPage(driver);
        resultsPage = new ResultsPage(driver);
        saveCalculationPage = new SaveCalculationPage(driver);
        myAccountPage = new MyAccountPage(driver);
        websiteHeaderPage = new WebsiteHeaderPage(driver);
    }

    @Test
    public void testCarbohydrateCalculationWorkflow() {
        driver.get("https://www.calculator.net/");

        // Login
        loginPage.openLoginForm();
        loginPage.login();
        myAccountPage.validateMyAccountPageHeader();

        // Search for carbohydrate calculator
        myAccountPage.searchAndClickCalculatorItem("carbohydrate");
        carbohydrateCalculatorPage.validateCarbohydrateCalculatorPageHeader();

        // Fill form
        carbohydrateCalculatorPage.clearTheForm();
        carbohydrateCalculatorPage.fillForm();
        carbohydrateCalculatorPage.clickCalculate();

        // Click Calculate and validate results
        resultsPage.validateResultsPageHeader();
        resultsPage.usUnitsValuesValidation();
        resultsPage.clickSave();

        // Save calculation
        saveCalculationPage.validateSaveCalculationPageHeader();

        LocalDateTime now = LocalDateTime.now();
        String formattedTimestamp = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss.SSS"));
        String calculationName = "Test Name ";
        String calculationDescription = "Test Description ";

        saveCalculationPage.fillSaveCalculationForm(
                calculationName + formattedTimestamp,
                calculationDescription + formattedTimestamp
        );
        saveCalculationPage.clickSave();
        saveCalculationPage.validateSavedCalculationSuccessMessage();

        // Go to My Account and validate saved calculation
        websiteHeaderPage.openMyAccount();
        System.out.println("Names: " + calculationName + formattedTimestamp);
        myAccountPage.verifyNameAndDescriptionOfSavedItem(calculationName + formattedTimestamp,
                calculationDescription + formattedTimestamp);
    }

    @Test
    public void TC04_CalculateEmptyForm() {
        driver.get("https://www.calculator.net/");

        // Login
        loginPage.openLoginForm();
        loginPage.login();
        myAccountPage.validateMyAccountPageHeader();

        // Search for carbohydrate calculator
        myAccountPage.searchAndClickCalculatorItem("carbohydrate");
        carbohydrateCalculatorPage.validateCarbohydrateCalculatorPageHeader();

        // Fill the age field, click Calculate and validate results
        carbohydrateCalculatorPage.clearTheForm();
        carbohydrateCalculatorPage.clickCalculate();
        carbohydrateCalculatorPage.validateErrorMessages();
    }

    @Test
    public void TC03_1_RemoveSavedCalculation() {
        driver.get("https://www.calculator.net/");

        // Login
        loginPage.openLoginForm();
        loginPage.login();
        myAccountPage.validateMyAccountPageHeader();

        // Search for carbohydrate calculator
        myAccountPage.searchAndClickCalculatorItem("carbohydrate");
        carbohydrateCalculatorPage.validateCarbohydrateCalculatorPageHeader();

        // Fill form
        carbohydrateCalculatorPage.clearTheForm();
        carbohydrateCalculatorPage.fillForm();
        carbohydrateCalculatorPage.clickCalculate();

        // Click Calculate and validate results
        resultsPage.validateResultsPageHeader();
        resultsPage.usUnitsValuesValidation();
        resultsPage.clickSave();

        // Save calculation
        saveCalculationPage.validateSaveCalculationPageHeader();

        LocalDateTime now = LocalDateTime.now();
        String formattedTimestamp = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss.SSS"));
        String calculationName = "Test Name ";
        String calculationDescription = "Test Description ";

        saveCalculationPage.fillSaveCalculationForm(
                calculationName + formattedTimestamp,
                calculationDescription + formattedTimestamp
        );
        saveCalculationPage.clickSave();
        saveCalculationPage.validateSavedCalculationSuccessMessage();

        // Go to My Account and validate saved calculation
        websiteHeaderPage.openMyAccount();
        myAccountPage.verifyNameAndDescriptionOfSavedItem(
                calculationName + formattedTimestamp,
                calculationDescription + formattedTimestamp
        );

        myAccountPage.removeLatestCalculation();
        myAccountPage.validateAbsenceOfSavedItem(
                calculationName + formattedTimestamp,
                calculationDescription + formattedTimestamp
        );
    }

    @AfterMethod
    public void teardown() {
        try {
            if (driver != null) {
                driver.quit(); // close browser and WebDriver session
            }
        } catch (Exception e) {
            System.err.println("Error during WebDriver cleanup: " + e.getMessage());
        } finally {
            driver = null; // prevent reuse of stale driver
        }
    }

}
