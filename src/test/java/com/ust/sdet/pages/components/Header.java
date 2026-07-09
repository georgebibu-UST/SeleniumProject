package com.ust.sdet.pages.components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.ust.sdet.pages.BasePage;
import com.ust.sdet.pages.CartPage;

public class Header extends BasePage {
    private static final By CART_ICON = By.cssSelector("[data-test='cart-icon']");

    public Header(WebDriver driver) {
        super(driver);
    }

    public CartBadge cartBadge() {
        return new CartBadge(wait);
    }

    public CartPage openCart() {
        click(CART_ICON);
        return new CartPage(driver);
    }
}
