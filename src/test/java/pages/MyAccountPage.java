package pages;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.openqa.selenium.Alert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class MyAccountPage extends BasePage {
    @FindBy(id = "calcSearchTerm")
    private WebElement searchField;
    @FindBy(id = "bluebtn")
    private WebElement searchButton;
    @FindBy(xpath = "//a[text()='Carbohydrate Calculator']")
    private WebElement carbohydrateCalculatorSearchItem;
    @FindBy(xpath = "//h1[text()='My Account']")
    private WebElement myAccountHeader;
    @FindBy(xpath = "(//li[contains(@id, 'savedlist')])[1]/img[@src='/img/svg/close.svg']")
    private WebElement latestSavedItemRemoveIcon;

    public MyAccountPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this); // Initializes @FindBy elements

    }

    public WebElement getSavedItemName(String name) {
        String xpath = String.format("//a[text()='%s']", name);
        return driver.findElement(By.xpath(xpath));
    }

    public WebElement getSavedItemDescription(String description) {
        String xpath = String.format("//li[text()='%s']", description);
        return driver.findElement(By.xpath(xpath));
    }

    public void verifyNameAndDescriptionOfSavedItem(String expectedName, String expectedDescription) {
        WebElement element_name = getSavedItemName(expectedName);
        WebElement element_description = getSavedItemDescription(expectedDescription);
        waitForElementAndAssertVisible(element_name, "Saved item name is not displayed: " + expectedName);
        waitForElementAndAssertVisible(element_description, "Saved item description is not displayed: " + expectedDescription);
    }

    public void validateAbsenceOfSavedItem(String expectedName, String expectedDescription) {
        String nameXpath = String.format("//a[contains(text(), '%s')]", expectedName);
        String descriptionXpath = String.format("//li[contains(text(), '%s')]", expectedDescription);

        validateAbsenceOfElementByLocator(By.xpath(nameXpath), "Saved item name is still present: " + expectedName);
        validateAbsenceOfElementByLocator(By.xpath(descriptionXpath), "Saved item description is still present: " + expectedDescription);
    }

    public void validateMyAccountPageHeader() {
        waitForElementAndAssertVisible(myAccountHeader, "The My Account header is not displayed on the page.");
    }

    public void searchAndClickCalculatorItem(String carbohydrate) {
        searchField.click();
        searchField.sendKeys(carbohydrate);
        carbohydrateCalculatorSearchItem.click();
    }

    public void removeLatestCalculation() {
        latestSavedItemRemoveIcon.click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.alertIsPresent());
        Alert alert = driver.switchTo().alert();
        alert.accept();
    }
}
