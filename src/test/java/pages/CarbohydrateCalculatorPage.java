package pages;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import java.util.Random;
import java.time.Duration;

public class CarbohydrateCalculatorPage extends BasePage {
    private Random random = new Random();

    @FindBy(xpath = "//h1[text()='Carbohydrate Calculator']")
    private WebElement carbohydrateCalculatorHeader;
    @FindBy(xpath = "//a[text()='+ Settings']")
    private WebElement settingsMenu;
    @FindBy(id = "cage")
    private WebElement ageField;
    @FindBy(css = "label[for='csex1']")
    private WebElement genderMaleRadioButton;
    @FindBy(css = "label[for='csex2']")
    private WebElement genderFemaleRadioButton;
    @FindBy(id = "cheightmeter")
    private WebElement heightField;
    @FindBy(id = "cheightfeet")
    private WebElement heightFeetField;
    @FindBy(id = "cheightinch")
    private WebElement heightInchesField;
    @FindBy(id = "cpound")
    private WebElement weightPoundsField;
    @FindBy(id = "ckg")
    private WebElement weightKilogramsField;
    @FindBy(id = "cactivity")
    private WebElement activityField;
    @FindBy(css = "label[for='cformula1']")
    private WebElement bmrMiffinFormulaRadiobutton;
    @FindBy(css = "label[for='cformula2']")
    private WebElement bmrKatchFormulaRadiobutton;
    @FindBy(name = "cfatpct")
    private WebElement bmrBodyFatField;
    @FindBy(css = "input[value='Calculate']")
    private WebElement calculateButton;
    @FindBy(css = "input[value='Clear']")
    private WebElement clearButton;
    @FindBy(xpath = "//font[text()='Please provide an age between 18 and 80.']")
    private WebElement ageFieldErrorMessage;
    @FindBy(xpath = "//font[text()='Please provide positive height value.']")
    private WebElement heightFieldErrorMessage;
    @FindBy(xpath = "//font[text()='Please provide positive weight value.']")
    private WebElement weightFieldErrorMessage;

    public CarbohydrateCalculatorPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this); // Initializes @FindBy
    }

    public void fillForm() {
        int age = random.nextInt(63) + 18; // 18–80 (80 - 18 = 62 + 1)
        int weightPounds = random.nextInt(211) + 90; // 90–300 (300 - 90 = 210 + 1)
        int totalHeightInches = random.nextInt(29) + 55; // 55–83 inches (4'7"–6'11", 83 - 55 = 28 + 1)
        int heightFeet = totalHeightInches / 12; // 4–6 feet
        int heightInches = totalHeightInches % 12; // 0–11 inches
        int bmrFat = random.nextInt(49) + 3;

        fillForm(age, weightPounds, heightFeet, heightInches, bmrFat);
    }

    public void fillForm(int age, double weightPounds, int heightFeet, int heightInches, int bmrFat) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Ensure fields are visible and interactable
        wait.until(ExpectedConditions.visibilityOf(ageField));
        wait.until(ExpectedConditions.visibilityOf(weightPoundsField));
        wait.until(ExpectedConditions.visibilityOf(heightFeetField));
        wait.until(ExpectedConditions.visibilityOf(heightInchesField));

        ageField.clear();
        weightPoundsField.clear();
        heightFeetField.clear();
        heightInchesField.clear();

        ageField.sendKeys(String.valueOf(age));
        weightPoundsField.sendKeys(String.valueOf(weightPounds));
        heightFeetField.sendKeys(String.valueOf(heightFeet));
        heightInchesField.sendKeys(String.valueOf(heightInches));

        genderMaleRadioButton.click();
        expandSettingsMenu();
        bmrMiffinFormulaRadiobutton.click();
        bmrBodyFatField.clear();
        bmrBodyFatField.sendKeys(String.valueOf(bmrFat));
    }

    public void validateCarbohydrateCalculatorPageHeader() {
        waitForElementAndAssertVisible(carbohydrateCalculatorHeader, "Element not visible");
    }

    public void expandSettingsMenu() {
        settingsMenu.click();
    }

    public void clickCalculate() {
        calculateButton.click();
    }

    public void clearTheForm() {
        clearButton.click();
    }

    public void validateErrorMessages() {
        waitForElementAndAssertVisible(ageFieldErrorMessage,
                "Error message for Age field didn't appear");
        waitForElementAndAssertVisible(weightFieldErrorMessage,
                "Error message for Weight field didn't appear");
        waitForElementAndAssertVisible(heightFieldErrorMessage,
                "Error message for Height field didn't appear");
    }
}
