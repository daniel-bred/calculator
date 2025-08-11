package pages;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

public class BasePage {
    protected WebDriver driver;

    public BasePage(WebDriver driver) {
        this.driver = driver;
    }

    public void waitForElementAndAssertVisible(WebElement element, String errorMessage) {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOf(element));
        Assert.assertTrue(element.isDisplayed(), errorMessage);
    }

    public void validateAbsenceOfElementByLocator(By locator, String errorMessage) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        boolean isAbsent = wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
        Assert.assertTrue(isAbsent, errorMessage);
    }
}