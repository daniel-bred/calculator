package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class WebsiteHeaderPage extends BasePage{
    @FindBy(xpath = "//a[text()='my account']")
    private WebElement myAccount;

    public WebsiteHeaderPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this); // Initializes @FindBy elements

    }

    public void openMyAccount() {
        myAccount.click();
    }

}
