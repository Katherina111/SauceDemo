package by.teachmeskills;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


public class LoginTest extends BaseTest {

    @DataProvider
    public Object[][] loginPageInValidateResults() {
        return new Object[][] {
                {"locked_out_user", "secret_sauce", "Epic sadface: Sorry, this user has been locked out."},
                {"", "", "Epic sadface: Username is required"},
                {"  ", "  ", "Epic sadface: Username and password do not match any user in this service"},
                {"standard_user", "", "Epic sadface: Password is required"},
                {"", "secret_sauce", "Epic sadface: Username is required"},
                {"123", "secret_sauce", "Epic sadface: Username and password do not match any user in this service"},
                {"123", "123", "Epic sadface: Username and password do not match any user in this service"},
                {"@#$", "secret_sauce", "Epic sadface: Username and password do not match any user in this service"},
                {"test", "secret_sauce", "Epic sadface: Username and password do not match any user in this service"}};
    }

    @Test(dataProvider = "loginPageInValidateResults")
    public void checkUnsuccessfulLoginPage(String login, String password, String expMessage) {
        driver.get("https://www.saucedemo.com/");
        driver.findElement(By.id("user-name")).sendKeys(String.valueOf(login));
        driver.findElement(By.id("password")).sendKeys(String.valueOf(password));
        driver.findElement(By.id("login-button")).submit();

        WebElement errorMessageOnLoginPage = driver.findElement(By.xpath("//div[@class ='error-message-container error']"));
        Assert.assertEquals(errorMessageOnLoginPage.getText(), expMessage, "Attention! Contains an error");
    }

    @DataProvider
    public Object[][] loginPageValidateResults() {
        return new Object[][] {
                {"standard_user", "secret_sauce", true},
                {"problem_user", "secret_sauce", true},
                {"performance_glitch_user", "secret_sauce", true}};
    }

    @Test(dataProvider = "loginPageValidateResults")
    public void checkSuccessfulLoginPage(String login, String password, boolean expProductPage) {
        driver.get("https://www.saucedemo.com/");
        driver.findElement(By.id("user-name")).sendKeys(String.valueOf(login));
        driver.findElement(By.id("password")).sendKeys(String.valueOf(password));
        driver.findElement(By.id("login-button")).submit();

        WebElement productPage = driver.findElement(By.xpath("//span[text( )= 'Products']"));
        Assert.assertEquals(productPage.isDisplayed(), expProductPage, "Attention! Contains an error");
    }
}
