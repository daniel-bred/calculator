package tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TC05_Age_Input_Validation_Negative {
    private WebDriver driver;
    private LoginPage loginPage;
    private CarbohydrateCalculatorPage carbohydrateCalculatorPage;
    private ResultsPage resultsPage;
    private SaveCalculationPage saveCalculationPage;
    private MyAccountPage myAccountPage;
    private WebsiteHeaderPage websiteHeaderPage;

    @BeforeClass
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();

        loginPage = new LoginPage(driver);
        carbohydrateCalculatorPage = new CarbohydrateCalculatorPage(driver);
        resultsPage = new ResultsPage(driver);
        saveCalculationPage = new SaveCalculationPage(driver);
        myAccountPage = new MyAccountPage(driver);
        websiteHeaderPage = new WebsiteHeaderPage(driver);
    }

    @Test
    public void removeSavedCalculation() {
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

        LocalDate today = LocalDate.now();
        String formattedDate = today.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        saveCalculationPage.fillSaveCalculationForm(
                "Test Name " + formattedDate,
                "Test Description " + formattedDate
        );
        saveCalculationPage.clickSave();
        saveCalculationPage.validateSavedCalculationSuccessMessage();

        // Go to My Account and validate saved calculation
        websiteHeaderPage.openMyAccount();
        myAccountPage.verifyNameAndDescriptionOfSavedItem("Test Name " + formattedDate,
                "Test Description " + formattedDate);
    }

    @AfterClass
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }

}
