package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage extends BasePage {

    @FindBy(id = "login")
    private WebElement signInButton;
    @FindBy(name = "email")
    private WebElement usernameField;
    @FindBy(name = "password")
    private WebElement passwordField;
    @FindBy(name = "submit")
    private WebElement loginSubmitButton;

    private final String USERNAME = "dan.bredesku271@gmail.com";
    private final String PASSWORD = "A6HBdDFm!r7yjjX";

    public LoginPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public void openLoginForm() {
        signInButton.click();
    }

    public void login() {
        usernameField.sendKeys(USERNAME);
        passwordField.sendKeys(PASSWORD);
        loginSubmitButton.click();
    }
}