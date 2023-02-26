package by.teachmeskills;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CartTest extends BaseTest{
    @Test
    public void validateProductDescriptionInTheCart() {
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
    }
}
