package by.teachmeskills;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class CheckoutTest extends BaseTest{

    @DataProvider
    public Object[][] registrationDataCheckoutPageValidateResults() {
        return new Object[][]{
                {"Anna", "Ivanova", 12345, },
                {"Anna", "", 12345, "Error: Last Name is required"},
                {"", "Ivanova", 12345, "Error: First Name is required"},
                {"Anna", "Ivanova", "Error: Postal Code is required"},
                {"", "", "Error: First Name is required"}};
    }
    @Test(dataProvider = "registrationDataCheckoutPageValidateResults")
    public void validateCheckoutPage(String first_name, String second_name, int zipCode) {
        driver.get("https://www.saucedemo.com/");
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).submit();

        final String productName = "Sauce Labs Backpack";
        String productCardLocator ="//div[text()='%s']/ancestor::div[@class='inventory_item']";
        String addToCartButtonLocator = productCardLocator + "//button[text()='Add to cart']";
        driver.findElement(By.xpath(String.format(addToCartButtonLocator, productName))).click();

        String productPriceLocator = productCardLocator + "//div[@class='inventory_item_price']";
        String expectedPrice = driver.findElement(By.xpath(String.format(productPriceLocator, productName))).getText();
        driver.findElement(By.id("shopping_cart_container")).click();

        String productInTheCart = "//div[text()='%s']/ancestor::div[@class='cart_item']";
        Assert.assertTrue(driver.findElement(By.xpath(String.format(productInTheCart, productName))).isDisplayed(),
                "Product has not been added to the cart");
        String productInCartPriceLocator = productInTheCart + "//div[@class='inventory_item_price']";
        String actualPrice = driver.findElement(By.xpath(String.format(productInCartPriceLocator, productName))).getText();

        Assert.assertEquals(actualPrice, expectedPrice,
                "Product should be added in the cart with the same price as at the product page");

        driver.findElement(By.xpath("//button[@id ='checkout']")).click();
        WebElement buttonCheckout = driver.findElement(By.xpath("//span[text()='Checkout: Your Information']"));
        Assert.assertTrue(buttonCheckout.isDisplayed(), "Redirect to wrong page");

        driver.findElement(By.id("first-name")).sendKeys(String.valueOf(first_name));
        driver.findElement(By.id("last-name")).sendKeys(String.valueOf(second_name));
        driver.findElement(By.id("postal-code")).sendKeys(String.valueOf(zipCode));
        driver.findElement(By.id("continue")).submit();

        WebElement checkoutOverviewPage = driver.findElement(By.xpath("//span[text()='Checkout: Overview']"));
        Assert.assertEquals(checkoutOverviewPage.isDisplayed(), "Attention! The code contains an error");

        driver.findElement(By.id("finish")).submit();
        WebElement checkoutCompletePage = driver.findElement(By.xpath("//span[text()='Checkout: Complete!']"));
        Assert.assertEquals(checkoutCompletePage.isDisplayed(), "Attention! The code contains an error");
    }
}
