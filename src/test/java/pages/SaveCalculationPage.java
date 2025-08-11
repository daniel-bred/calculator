package pages;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

public class SaveCalculationPage extends BasePage{
    @FindBy(xpath = "//h1[text()='Save Calculation']")
    private WebElement saveCalculationHeader;
    @FindBy(css = "[name='submit']")
    private WebElement saveIcon;
    @FindBy(css = "[name='usertitle']")
    private WebElement resultName;
    @FindBy(css = "[name='usernote']")
    private WebElement resultDescription;
    @FindBy(xpath = "//p[contains(text(),'calculation has been saved')]")
    private WebElement saveConfirmationMessage;



    public SaveCalculationPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public void fillSaveCalculationForm(String name, String description) {
        resultName.clear();
        resultName.sendKeys(name);

        resultDescription.clear();
        resultDescription.sendKeys(description);

    }

    public void clickSave() {
        saveIcon.click();
    }

    public void validateSaveCalculationPageHeader() {
        waitForElementAndAssertVisible(saveCalculationHeader,
                "The 'Save Calculation' header is not displayed on the page.");
    }

    public void validateSavedCalculationSuccessMessage() {
        waitForElementAndAssertVisible(saveCalculationHeader,
                "The success message for saved table is not displayed on the page.");
    }
}
