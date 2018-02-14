package SolidOpinion;

import SolidOpinion.pages.BuyPoints;
import SolidOpinion.pages.Page;
import SolidOpinion.pages.SignUp;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;


public class SampleTestNgTest extends TestNgTestBase {
  Page commentsPage = new Page(driver);
  SignUp signUp = new SignUp(driver);
  BuyPoints paymentPage = new BuyPoints(driver);

  By commetsPageTitle = By.tagName("h3");
  By topCommentsFrame = By.xpath("//*[text()='TOP Comments']");
  By promoteButton = By.id("promote_open");
  By signUpConfirmation = By.xpath("//*[text()='Your temporary password has been sent to your inbox. Please don’t forget to change it later.']");
  String user;
  By userConfirmation = By.xpath("//div[@class='top-promo-header clearfix current-user-top-menu-container']/div[1][contains(.//span, '"+user+"')]");
  By enforcer= By.xpath("//div[@class='top-promo-header clearfix current-user-top-menu-container']/div[2][contains(.//span, 'Enforcer')]");
  By commentInput = By.id("comment_data");
  By message = By.id("message_box");
  By promoteInPopUp = By.xpath("//div/button[contains(@id, 'post_bid_message') and contains(text(), 'Promote')]");
  By creditCardButton = By.xpath("//*[@id='stripeButton' and text()='Pay with Credit Card']");
  By payPalButton = By.xpath("//*[@id='paypalButton' and text()='Pay with PayPal']");
  By commentReply = By.xpath("//div[@class='comment-left-box']/a[contains(text(), 'Margaret')]/../../../ul/li[contains(@class, 'reply-link-container')]/a");
  By commentReplyInput = By.xpath("//*[@data-ph='Leave a reply']");
  By postButton = By.xpath("//*[@value='Post']");



  @Test(priority=0)
  public void testCommentsPageIsOpened() {
      commentsPage
              .openCommetsPage(driver);
      Assert.assertTrue(driver.findElement(commetsPageTitle).getText().equals("Promoted comments"));
  }

  @Test(priority=1)
  public void openTopCommentsFrame() {
      commentsPage
              .openFrame(driver);
      Assert.assertTrue(driver.findElement(topCommentsFrame).isDisplayed());
  }

  @Test(priority=2)
    public void addCommentWithoutAuthorization(){
      commentsPage
              .openFrame(driver)
              .addComment(driver, commentInput, "comment without authorization");
      Assert.assertFalse(driver.findElement(promoteButton).isDisplayed());
  }

  @Test(priority=3)
    public void signUpVerification() throws Exception{
      String email = "testEmail"+signUp
              .generateRandomDigits()+"@test.com";
      user = "testUser"+signUp
              .generateRandomDigits();
      By userConfirmation = By.xpath("//div[@class='top-promo-header clearfix current-user-top-menu-container']/div[1][contains(.//span, '"+user+"')]");
      commentsPage
              .openFrame(driver)
              .click(driver, commentInput);
      signUp
              .inputSignUpForm(driver, email, user)
              .confirmAgreement(driver)
              .clickSingUpButton(driver);
      Assert.assertTrue(driver.findElement(signUpConfirmation).isDisplayed());
      Assert.assertTrue(driver.findElement(enforcer).isDisplayed());
      Assert.assertTrue(driver.findElement(userConfirmation).isDisplayed());
  }

  @Test(priority=4)
    public void promoteWithoutPoints() throws Exception {
    String email = "testEmail"+signUp
            .generateRandomDigits()+"@test.com";
    user = "testUser"+signUp
            .generateRandomDigits();
    commentsPage
            .openFrame(driver)
            .click(driver, commentInput);
    signUp
            .inputSignUpForm(driver, email, user)
            .confirmAgreement(driver)
            .clickSingUpButton(driver);
    commentsPage
            .addComment(driver, commentInput,"new comment for promote")
            .click(driver, promoteButton);
    commentsPage
            .checkPaymentPage(driver, promoteInPopUp);
    System.out.println(driver.findElement(message).getText());
    Assert.assertTrue(driver.findElement(message).getText().contains("Not enough points, please buy at least"));
  }

  @Test(priority=5)
  public void promoteBuyPoints() throws Exception{
    String email = "testEmail"+signUp
            .generateRandomDigits()+"@test.com";
    user = "testUser"+signUp
            .generateRandomDigits();
    commentsPage
            .openFrame(driver)
            .click(driver, commentInput);
    signUp
            .inputSignUpForm(driver, email, user)
            .confirmAgreement(driver)
            .clickSingUpButton(driver);
    commentsPage
            .addComment(driver, commentInput,"new comment for promote")
            .click(driver, promoteButton)
            .click(driver,promoteInPopUp);
    String curWin = paymentPage
            .navigateToChild(driver);
    commentsPage.click(driver, creditCardButton);
    paymentPage
            .inputCardDetails(driver, "1111222233334444", "01/30", "999", "000000000");
    driver.close();
    paymentPage
            .navigateToParent(driver, curWin);

  }
  @Test(priority=6)
  public void replyOnComment() throws Exception {
    String comment = "reply on comment number " + signUp.generateRandomDigits();
    String email = "testEmail"+signUp
            .generateRandomDigits()+"@test.com";
    user = "testUser"+signUp
            .generateRandomDigits();
    commentsPage
            .openFrame(driver)
            .click(driver, commentReply);
    signUp
            .inputSignUpForm(driver, email, user)
            .confirmAgreement(driver)
            .clickSingUpButton(driver);
    commentsPage
            .click(driver, commentReply)
            .addComment(driver, commentReplyInput, comment)
            .click(driver, postButton);
    Thread.sleep(8000);
    Assert.assertTrue(driver.findElement(By.xpath("//span[@class='comment-text' and text()='"+comment+"']/../../ul[contains(.//span, 'Pending moderation')]")).isDisplayed());

  }

}
