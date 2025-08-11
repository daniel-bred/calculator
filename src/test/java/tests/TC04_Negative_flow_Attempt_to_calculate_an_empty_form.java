package tests;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.*;

public class TC04_Negative_flow_Attempt_to_calculate_an_empty_form {
    private WebDriver driver;
    private LoginPage loginPage;
    private CarbohydrateCalculatorPage carbohydrateCalculatorPage;
    private MyAccountPage myAccountPage;

    @BeforeClass
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();

        loginPage = new LoginPage(driver);
        carbohydrateCalculatorPage = new CarbohydrateCalculatorPage(driver);
        myAccountPage = new MyAccountPage(driver);
    }

    @Test
    public void testCalculateEmptyForm() {
        driver.get("https://www.calculator.net/");

        // Login
        loginPage.openLoginForm();
        loginPage.login();
        myAccountPage.validateMyAccountPageHeader();

        // Search for carbohydrate calculator
        myAccountPage.searchAndClickCalculatorItem("carbohydrate");
        carbohydrateCalculatorPage.validateCarbohydrateCalculatorPageHeader();

        // Fill the age field, click Calculate and validate results
        carbohydrateCalculatorPage.clickCalculate();
        carbohydrateCalculatorPage.validateErrorMessages();
    }

    @AfterClass
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }

}
