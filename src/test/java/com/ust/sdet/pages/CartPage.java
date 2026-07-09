package com.ust.sdet.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.Alert;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class CartPage extends BasePage{
    private static final By CART_PAGE = By.cssSelector("[data-test='cart-page']");
    private static final By LINES = By.cssSelector("[data-test='cart-line']");
    private static final By TOTAL = By.cssSelector("[data-test='cart-total']");
    private static final By CHECKOUT = By.cssSelector("[data-test='checkout-button']");
    private static final By REMOVE_BUTTON = By.xpath(".//button[starts-with(normalize-space(),'Remove')]");

    public CartPage(WebDriver driver) {
        super(driver);
        visible(CART_PAGE);
    }

    public int lineCount() {
        return elements(LINES).size();
    }

    public String total() {
        return text(TOTAL);
    }

    public CheckoutPage proceed() {
        click(CHECKOUT);
        return new CheckoutPage(driver);
    }

    public CartPage removeFirstItem() {
        WebElement firstLine = elements(LINES).get(0);
        WebElement removeBtn = firstLine.findElement(REMOVE_BUTTON);

        ((org.openqa.selenium.JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView({block:'center'});", removeBtn);

        wait.until(ExpectedConditions.elementToBeClickable(removeBtn));

        ((org.openqa.selenium.JavascriptExecutor) driver)
                .executeScript("arguments[0].click();", removeBtn);

        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        alert.accept();

        return this;
    }
}
