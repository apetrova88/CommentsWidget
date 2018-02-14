package SolidOpinion.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Abstract class representation of a Page in the UI. Page object pattern
 */
public class Page {

  WebDriver driver;
  By commentInput = By.id("comment_data");
  By promoteButton = By.id("promote_open");
  By paymentPage = By.className("header-center");
  By commentReplyInput = By.xpath("//*[@data-ph='Leave a reply']");
  BuyPoints paymentObj = new BuyPoints(driver);
  By topCommentsFrame = By.xpath("//*[text()='TOP Comments']");


    public Page(WebDriver driver){
    this.driver = driver;
  }


  public Page openCommetsPage(WebDriver driver) {
    driver.get("https://s3.amazonaws.com/soliddemoonline/php/int/natalie/natalietest.html");
    return this;
  }

  public String getTitle(WebDriver driver) {
    return driver.getTitle();
  }

  public Page openFrame(WebDriver driver) {
      openCommetsPage(driver);
      String apiComments = driver.findElement(By.tagName("iframe")).getAttribute("src");
      driver.get(apiComments);
      WebDriverWait wait = new WebDriverWait(driver, 10);
      wait.until(ExpectedConditions.visibilityOfElementLocated(topCommentsFrame));
      return this;
  }
  public Page addComment(WebDriver driver, By commentField, String comment){
      driver.findElement(commentField).sendKeys(comment);
      return this;
  }

  public Page click(WebDriver driver, By locator) throws Exception {
      WebDriverWait wait = new WebDriverWait(driver, 10);
      wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
      Thread.sleep(3000);
      driver.findElement(locator).click();
      return this;
  }

  public Page checkPaymentPage(WebDriver driver, By locator) throws Exception {
      String currentWindow = driver.getWindowHandle();
      click(driver, locator);
      Thread.sleep(10000);
      for (String newWindow : driver.getWindowHandles()) {
          driver.switchTo().window(newWindow);
      }
      paymentObj.paymentPageTitle(driver);
      driver.close();
      driver.switchTo().window(currentWindow);
      driver.switchTo().defaultContent();
      return this;
  }


}
