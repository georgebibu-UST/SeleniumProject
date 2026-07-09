package com.ust.sdet.tests;

import com.ust.sdet.support.Config;
import com.ust.sdet.support.DriverFactory;

import java.time.Duration;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CatalogFlowTest {
    private static final By SEARCH_INPUT = By.cssSelector("[data-test='search-input']");
    private static final By PRODUCT_CARD = By.cssSelector("[data-test='product-card']");
    private static final By PRODUCT_TITLE = By.cssSelector("[data-test='product-title']");
    private static final By PRODUCT_PRICE = By.cssSelector("[data-test='product-price']");
    private static final By PRODUCT_LINK = By.cssSelector("[data-test='product-card'] a");
    private static final By DETAIL_NAME = By.cssSelector("[data-test='detail-name']");
    private static final By ADD_TO_CART = By.cssSelector("[data-test='add-to-cart']");
    private static final By CART_COUNT = By.cssSelector("[data-test='cart-count']");
    private static final By SORT_SELECT = By.cssSelector("[data-test='sort-select']");
    private static final By RESULT_COUNT = By.cssSelector("[data-test='catalog-result-count']");
    private static final By EMPTY_SEARCH = By.cssSelector("[data-test='empty-search']");
    private static final By CATEGORY_FILTER = By.id("category-filter");

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    void setup() {
        driver = DriverFactory.createChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get(Config.catalogUrl());
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    @DisplayName("Search waits for catalog cards and validates matching results")
    void searchShowsMatchingCards() {
        search("headphones", "Showing 1 product");

        //replaced findElements with a visibility check
        List<WebElement> cards = wait.until(
            ExpectedConditions.visibilityOfAllElementsLocatedBy(PRODUCT_CARD)
        );

        //Asserting that the displayed count matches the fetched count
        String resultText = driver.findElement(RESULT_COUNT).getText();
        assertTrue(resultText.contains(String.valueOf(cards.size())),
            "Mismatch between UI result count and actual cards");
            
        for(WebElement card: cards) {
            String title = card.findElement(PRODUCT_TITLE).getText();
            assertAll(
                () -> assertTrue(title.toLowerCase().contains("headphone"), "unrelated result: " + title),
                () -> assertTrue(card.findElement(PRODUCT_PRICE).isDisplayed(), "price missing for: " + title),
                //added assertion to check link
                () -> assertTrue(card.findElement(By.tagName("a")).isDisplayed(), "Link missing in card")
            );
        }
    }

    @Test
    @DisplayName("Product detail opens and add-to-cart updates the cart badge")
    void detailAddToCartUpdatesBadge() {

        //Capture product name before adding to cart
        String productNameFromCatalog = wait.until(ExpectedConditions.visibilityOfElementLocated(PRODUCT_TITLE)).getText();
        wait.until(ExpectedConditions.elementToBeClickable(PRODUCT_LINK)).click();

        WebElement detailName = wait.until(ExpectedConditions.visibilityOfElementLocated(DETAIL_NAME));
        assertAll(
            () -> assertTrue(driver.getCurrentUrl().contains("/product/")),
            () -> assertFalse(detailName.getText().isBlank()),
            //assert that product name is correct
            () -> assertEquals(productNameFromCatalog, detailName.getText(), "Product name mismatch")
        );

        wait.until(ExpectedConditions.elementToBeClickable(ADD_TO_CART)).click();
        wait.until(ExpectedConditions.urlContains(("/cart")));
        wait.until(ExpectedConditions.textToBe(CART_COUNT, "1"));
    }

    @Test
    @DisplayName("Sort dropdown re-renders cards and prices become ascending")
    void sortLowToHighShowsAscendingPrices() {
        WebElement oldFirstCard = wait.until(ExpectedConditions.visibilityOfElementLocated(PRODUCT_CARD));

        new Select(driver.findElement(SORT_SELECT)).selectByVisibleText("Price: Low to High");

        wait.until(ExpectedConditions.stalenessOf(oldFirstCard));
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(PRODUCT_CARD, 0));

        List<Integer> prices = driver.findElements(PRODUCT_PRICE).stream().map((element) -> Integer.parseInt(element.getText().replaceAll("[^0-9]", ""))).toList();

        assertEquals(prices.stream().sorted().toList(), prices);
    }

    @Test
    @DisplayName("Search with no results shows empty state")
    void searchShowsEmptyState() {
        search("nonexistentproduct123", "Showing 0 products");

        WebElement emptyState = wait.until(
            ExpectedConditions.visibilityOfElementLocated(EMPTY_SEARCH)
        );

        assertAll(
            () -> assertTrue(emptyState.isDisplayed(), "Empty state not visible"),
            () -> assertTrue(driver.findElements(PRODUCT_CARD).isEmpty(), "Product cards should not be displayed for empty search"),
            () -> assertEquals("Showing 0 products", driver.findElement(RESULT_COUNT).getText(), "Incorrect result count for empty search")
        );

    }

    @Test
    @DisplayName("Search by category")
    void searchByCategory() {
        new Select(driver.findElement(CATEGORY_FILTER)).selectByVisibleText("Footwear");

        WebElement card = wait.until(
            ExpectedConditions.visibilityOfElementLocated(PRODUCT_CARD)
        );

        assertAll(
            () -> assertTrue(card.isDisplayed()),
            () -> assertFalse(driver.findElements(PRODUCT_CARD).isEmpty(), "Product cards should be displayed"),
            () -> assertEquals("Showing 1 product", driver.findElement(RESULT_COUNT).getText(), "Incorrect result count")
        );
    }

    @Test
    @DisplayName("Search is case-insensitive")
    void searchIsCaseInsensitive() {
        search("HEADPHONES", "Showing 1 product");

        List<WebElement> cards = driver.findElements(PRODUCT_CARD);

        for(WebElement card: cards) {
            String title = card.findElement(PRODUCT_TITLE).getText();
            assertAll(
                () -> assertTrue(title.toLowerCase().contains("headphone"), "unrelated result: " + title),
                () -> assertTrue(card.findElement(PRODUCT_PRICE).isDisplayed(), "price missing for: " + title),
                //added assertion to check link
                () -> assertTrue(card.findElement(By.tagName("a")).isDisplayed(), "Link missing in card")
            );
        }
    }

    private void search(String query, String exepectedResultCount) {
        WebElement searchInput = wait.until(ExpectedConditions.visibilityOfElementLocated(SEARCH_INPUT));
        searchInput.clear();
        searchInput.sendKeys(query);
        searchInput.sendKeys(Keys.ENTER);
        wait.until(ExpectedConditions.textToBe(RESULT_COUNT, exepectedResultCount));
    }
}
