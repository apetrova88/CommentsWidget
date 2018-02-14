package SolidOpinion.pages;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

public class BuyPoints {
    WebDriver driver;
    By paymentPage = By.className("header-center");
    By promoteInPopUp = By.xpath("//div/button[contains(@id, 'post_bid_message') and contains(text(), 'Promote')]");
    By cardInput = By.xpath("//*[@placeholder='Card number']");
    By date = By.xpath("//*[@placeholder='MM / YY']");
    By cvCode = By.xpath("//*[@placeholder='CVC']");
    By telNum = By.xpath("//*[@inputmode='tel']");
    By payButton = By.className("Button-animationWrapper-child--primary");
    By closePayButton = By.xpath("//span[@class='Header-navClose']");
    By checkBox = By.xpath("//a[@class='Checkbox']");
    By stripleFrame = By.className("stripe_checkout_app");



    public BuyPoints(WebDriver driver) {
        this.driver = driver;
    }

    public BuyPoints paymentPageTitle(WebDriver driver) {
        driver.getTitle();
        return this;
    }


    public BuyPoints navigateToPaymentPage(WebDriver driver) throws InterruptedException {
        String currentWindow = driver.getWindowHandle();
        Thread.sleep(10000);
        for (String newWindow : driver.getWindowHandles()) {
            driver.switchTo().window(newWindow);
        }
        driver.findElement(paymentPage);
        /*driver.switchTo().window(currentWindow);
        driver.switchTo().defaultContent();*/
        return this;
    }

    public String navigateToChild(WebDriver driver) throws InterruptedException {
        String currentWindow=driver.getWindowHandle();
        Thread.sleep(10000);
        for (String newWindow : driver.getWindowHandles()) {
            driver.switchTo().window(newWindow);
        }
        return currentWindow;
    }

    public BuyPoints navigateToParent(WebDriver driver, String currentWindow) {
        driver.switchTo().window(currentWindow);
        return this;
    }


    public BuyPoints inputCardDetails(WebDriver driver, String cardNumber, String expDate, String cvc, String telephone)  {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(stripleFrame));
        driver.switchTo().frame("stripe_checkout_app");
        driver.findElement(cardInput).sendKeys(cardNumber);
        driver.findElement(date).sendKeys(expDate);
        driver.findElement(cvCode).sendKeys(cvc);
        driver.findElement(checkBox).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(telNum));
        driver.findElement(telNum).sendKeys(telephone);
        driver.findElement(payButton).click();
        driver.findElement(closePayButton).click();
        driver.switchTo().defaultContent();
        return this;
    }

}


