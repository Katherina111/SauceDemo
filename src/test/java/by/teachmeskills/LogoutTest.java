package by.teachmeskills;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LogoutTest extends BaseTest {
    @Test
    public void checkLogOutPage() {
        driver.get("https://www.saucedemo.com/");
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).submit();
        driver.findElement(By.xpath("//button[@id ='react-burger-menu-btn']")).click();
        driver.findElement(By.xpath("//a[@id ='logout_sidebar_link']")).click();
        WebElement loginButtonIsDisplayed = driver.findElement(By.id("login-button"));
        Assert.assertTrue(loginButtonIsDisplayed.isDisplayed(), "User is not logged out");
    }
}
