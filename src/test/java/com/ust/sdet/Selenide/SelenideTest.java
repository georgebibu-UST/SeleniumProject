package com.ust.sdet.Selenide;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.CollectionCondition.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.codeborne.selenide.ElementsCollection;

import static com.codeborne.selenide.Selenide.page;

class SelenideTest {
    @Test
    @DisplayName("Catalog search to results flow (Page Object)")
    void searchToResultsFlow() {

        CatalogPage catalog = page(CatalogPage.class).openPage().searchFor("electronics");

        ElementsCollection cards = catalog.cards().shouldBe(sizeGreaterThan(0));

        String resultText = catalog.resultCount().shouldBe(visible).getText();

        assertTrue(resultText.contains(String.valueOf(cards.size())), "Mismatch between UI result count");

        cards.shouldHave(allMatch("All cards contain keyword",
                element -> element.getText().toLowerCase().contains("electronics")
        ));

        cards.shouldHave(size(2));
        cards.shouldHave(texts("Headphones", "Tablet"));

        cards.forEach(card -> assertAll(
                () -> card.$("[data-test='product-price']").shouldBe(visible),
                () -> card.$("a").shouldBe(visible),
                () -> card.shouldBe(enabled)
        ));

        catalog.prices().shouldHave(allMatch("Prices should not be empty",
                        element -> !element.getText().trim().isEmpty()
                ));

        cards.filterBy(text("Headphones")).shouldHave(size(1));
        cards.filterBy(text("Tablet")).shouldHave(size(1));
    }

    @Test
    @DisplayName("Search with no results shows empty state (Page Object)")
    void searchShowsEmptyState() {
        CatalogPage catalog = page(CatalogPage.class).openPage().searchFor("nonexistentproduct123");
        catalog.emptyState().shouldBe(visible);
        catalog.cards().shouldHave(size(0));
        catalog.resultCount().shouldHave(text("Showing 0 products"));
    }
}