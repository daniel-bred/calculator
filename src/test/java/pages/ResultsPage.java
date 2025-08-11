package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

public class ResultsPage extends BasePage{

    @FindBy(xpath = "//h2[text()='Result']")
    private WebElement resultHeader;
    @FindBy(css = "[title='Save this calculation']")
    private WebElement saveIcon;
    @FindBy(xpath = "(//td[contains(text(),'lb/week')])[1]")
    private WebElement lbTableElement;


    public ResultsPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);

    }

    public void validateResultsPageHeader() {
        waitForElementAndAssertVisible(resultHeader, "The 'Result' header is not displayed on the page.");
    }

    public void clickSave() {
        saveIcon.click();
    }

    public void usUnitsValuesValidation() {
        waitForElementAndAssertVisible(lbTableElement,"The table doesn't contain element with US Unit");

    }
}
