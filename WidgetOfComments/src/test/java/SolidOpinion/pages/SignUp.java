package SolidOpinion.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Random;

public class SignUp {
    WebDriver driver;

    By userEmail = By.className("f_email");
    By userName = By.className("f_name");
    By signUpButton = By.className("go_frontend_login");
    By agreement = By.className("i-agree");
    By enforcer= By.xpath("//div[@class='top-promo-header clearfix current-user-top-menu-container']/div[2][contains(.//span, 'Enforcer')]");


    public SignUp(WebDriver driver){
        this.driver = driver;
    }


    public String generateRandomDigits() {
        Random r = new Random( System.currentTimeMillis() );
        int randomDigits =  1000000 + r.nextInt(2000000);
        return String.valueOf(randomDigits);
    }

    public SignUp inputSignUpForm(WebDriver driver, String email, String user){
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(userEmail));
        wait.until(ExpectedConditions.visibilityOfElementLocated(userName));
        driver.findElement(userEmail).sendKeys(email);
        driver.findElement(userName).sendKeys(user);
        return this;
    }

    public SignUp confirmAgreement(WebDriver driver){
        driver.findElement(agreement).click();
        return this;
    }

    public SignUp clickSingUpButton(WebDriver driver){
        driver.findElement(signUpButton).click();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(enforcer));
        return this;
    }
}
